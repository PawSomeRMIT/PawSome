package com.example.pawsome.presentation.kycscreen.upload

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawsome.data.repository.BackEndRepo
import com.example.pawsome.data.repository.EKYCApiRepo
import com.example.pawsome.model.Booking
import com.example.pawsome.model.PetDetail
import com.example.pawsome.model.User
import com.example.pawsome.model.api_model.CheckLivenessBody
import com.example.pawsome.model.api_model.ExtractInfoBody
import com.example.pawsome.model.api_model.StripeResponse
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EKYCScreenViewModel @Inject constructor(
    private val backEndRepo: BackEndRepo,
    private val ekycRepo: EKYCApiRepo
) : ViewModel() {
    var isVeificationCompleted = mutableStateOf(false)
    var isPaymentCompleted = mutableStateOf(false)

    private val _isLoading = Channel<Boolean>()
    val isLoading = _isLoading.receiveAsFlow()

    private val _idName = MutableStateFlow("")
    val idName = _idName.asStateFlow()

    private val _idNumber = MutableStateFlow("")
    var idNumber = _idNumber.asStateFlow()

    suspend fun makePayment(context: Context, petDetail: PetDetail): StripeResponse {
        return backEndRepo.call_payment_sheet(petDetail.bookingPricePerDay.toDouble())
    }

    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when(paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                Log.d("StripeAPI", "Canceled")
            }

            is PaymentSheetResult.Failed -> {
                Log.d("StripeAPI", "Error: ${paymentSheetResult.error.message}")
            }

            is PaymentSheetResult.Completed -> {
                Log.d("StripeAPI", "Completed")

                isPaymentCompleted.value = true
            }
        }
    }

    suspend fun uploadImg(context: Context, uriFront: Uri, uriBack: Uri) {
        viewModelScope.launch {
            _isLoading.send(true)

            var file = File(context.cacheDir, "uploadImageFront.jpg")

            withContext(Dispatchers.IO) {
                file.createNewFile()
            }

            context.contentResolver.openInputStream(uriFront)?.use {input ->
                file.outputStream().use { output->
                    input.copyTo(output)
                }
            }

            val frontHash = ekycRepo.upload_image(file, "Front side", "Sample Description").`object`

            file = File(context.cacheDir, "uploadImageBack.jpg")

            withContext(Dispatchers.IO) {
                file.createNewFile()
            }

            context.contentResolver.openInputStream(uriBack)?.use {input ->
                file.outputStream().use { output->
                    input.copyTo(output)
                }
            }

            val backHash = ekycRepo.upload_image(file, "Back side", "Sample Description").`object`.hash

            Log.d("EKYC", frontHash.hash)
            Log.d("EKYC", frontHash.tokenId)
//            Log.d("EKYC", resultBack.`object`.hash)

            // Announce done uploading
            Toast.makeText(context, "Upload Images Successfully!", Toast.LENGTH_SHORT).show()

            val resultFront = checkLiveness(frontHash.hash)

            if (resultFront) {
//                val resultBack = checkLiveness(backHash)
//
//                if (resultBack) {
//                    Toast.makeText(context, "Successfully Verify Your ID!", Toast.LENGTH_SHORT).show()
//
//                    _isLoading.send(false)
//                }

                val requestBody = ExtractInfoBody(
                    img_front = frontHash.hash,
                    client_session = "ANDROID_nokia7.2_28_Simulator_2.4.2_08d2d8686ee5fa0e_1581910116532",
                    type = 2,
                    validate_postcode = true,
                    token = frontHash.tokenId
                    )

                val extractInfoResult = ekycRepo.extract_front_info(requestBody)

                _idNumber.value = extractInfoResult.`object`.id
                _idName.value = extractInfoResult.`object`.name

                Log.d("EKYC", extractInfoResult.`object`.toString())

                isVeificationCompleted.value = true

                _isLoading.send(false)

            }
            else {
                Toast.makeText(context, "Failed to Verify Your Front Side Card!", Toast.LENGTH_SHORT).show()

                _isLoading.send(false)
            }

        }
    }

    suspend fun checkLiveness(image_hash: String): Boolean {
        Log.d("EKYC", "Entered")

        val requestBody = CheckLivenessBody(img = image_hash, client_session = "ANDROID_nokia7.2_28_Simulator_2.4.2_08d2d8686ee5fa0e_1581910116532")

        val result = ekycRepo.check_liveness(requestBody)
        Log.d("EKYC", result.`object`.toString())

        Log.d("EKYC", (!result.`object`.fake_liveness).toString())

        return (!result.`object`.fake_liveness)
    }

    suspend fun createBooking(petDetail: PetDetail) {
        Log.d("EKYC", "CReateing")

        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()

            var userData = User()

            val auth = FirebaseAuth.getInstance()
            val userID = auth.currentUser?.uid

            val userRef = userID?.let {
                Firebase.firestore.collection("user").document(it)
            }

            val booking = Booking(
                customerId = userID!!,
                petId = petDetail.id!!,
                totalPrice = petDetail.bookingPricePerDay.toDouble(),
                customerCardIdName = idName.value,
                customerCardIdNumber = idNumber.value,
                petDetail = null
            )

            val snapshot = userRef?.get()?.await()

            snapshot?.let {
                userData = User(
                    userID = it.get("userID").toString(),
                    username = it.get("username").toString(),
                    email = it.get("email").toString(),
                    image = it.get("image").toString(),
                    membership = it.get("membership").toString(),
                    chatToken = it.get("chatToken").toString(),
                    history = it.get("history") as List<Booking>
                )
            }

            userData.history = userData.history.plus(booking)

            Log.d("AFTER PAY", userData.toString())

            val updatePetDetail = petDetail

            updatePetDetail.petStatus = "Not Available"

            val petSnapshot = db.collection("pets")
                .document(petDetail.id!!)
                .set(updatePetDetail)
                .await()

            userRef?.set(userData)?.addOnSuccessListener {
                Log.d("AFTER PAY", "Successfully update user")
            }?.addOnFailureListener {
                Log.d("AFTER PAY", "Inside_OnFailureListener")
                Log.d("AFTER PAY", "Exception= ${it.message}")
                Log.d("AFTER PAY", "Exception= ${it.localizedMessage}")
            }
        }
    }
}



