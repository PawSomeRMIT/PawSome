package com.example.pawsome.presentation.settings

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawsome.data.repository.BackEndRepo
import com.example.pawsome.model.Booking
import com.example.pawsome.model.PetDetail
import com.example.pawsome.model.User
import com.example.pawsome.model.api_model.StripeResponse
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val backEndRepo: BackEndRepo
) : ViewModel() {
    var isPaymentCompleted = mutableStateOf(false)

    suspend fun makePayment(context: Context, price: Double): StripeResponse {
        return backEndRepo.call_payment_sheet(price)
    }

    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
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

    suspend fun upgradeAccount() {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()

            var userData = User()

            val auth = FirebaseAuth.getInstance()
            val userID = auth.currentUser?.uid

            val userRef = userID?.let {
                Firebase.firestore.collection("user").document(it)
            }

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

            userData.membership = "Subscribed"

            Log.d("AFTER PAY", userData.toString())

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
