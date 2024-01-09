package com.example.pawsome.presentation.payment

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pawsome.api.EKYCApi
import com.example.pawsome.data.repository.BackEndRepo
import com.example.pawsome.data.repository.EKYCApiRepo
import com.example.pawsome.model.api_model.CheckLivenessBody
import com.example.pawsome.model.api_model.StripeResponse
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val backEndRepo: BackEndRepo,
    private val ekycRepo: EKYCApiRepo
) : ViewModel() {
    val _paymentState = Channel<Boolean>()
    val paymentState = _paymentState.receiveAsFlow()

    var isPaymentCompleted = mutableStateOf(false)

    suspend fun makePayment(context: Context): StripeResponse {
        return backEndRepo.call_payment_sheet()
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

    suspend fun uploadImg(file: File): String {
        val result = ekycRepo.upload_image(file, "The title", "Sample Description")

//        Log.d("EKYC", result.toString())

        return result.`object`.hash
    }

    suspend fun checkLiveness(image_hash: String) {
        Log.d("EKYC", "Entered")

        try {
            val requestBody = CheckLivenessBody(img = image_hash, client_session = "ANDROID_nokia7.2_28_Simulator_2.4.2_08d2d8686ee5fa0e_1581910116532")

            val result = ekycRepo.check_liveness(requestBody)

//            Log.d("EKYC", result.toString())
            Log.d("EKYC", result.`object`.toString())
        }
        catch (e: HttpException) {
            Log.d("EKYC", e.message.toString())
        }
    }
}