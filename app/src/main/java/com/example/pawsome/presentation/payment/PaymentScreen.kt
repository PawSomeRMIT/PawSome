package com.example.pawsome.presentation.payment

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pawsome.R
import com.example.pawsome.common.GradientButton
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.rememberPaymentSheet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun PaymentScreen(
    navController: NavHostController,
    paymentViewModel: PaymentViewModel = hiltViewModel()
) {
    val paymentSheet = rememberPaymentSheet(paymentViewModel::onPaymentSheetResult)

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    var customerConfig by remember {
        mutableStateOf<PaymentSheet.CustomerConfiguration?>(null)
    }

    var paymentIntentClientSecret by remember {
        mutableStateOf<String?>(null)
    }

    val greenGradient = Brush.verticalGradient(colors = listOf(colorResource(id = R.color.black), colorResource(id = R.color.light_gray)))

    // EKYC

    var uri by remember{
        mutableStateOf<Uri?>(Uri.parse(""))
    }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uri = it
        }
    )

    LaunchedEffect(key1 = context) {
        scope.launch {
            val result = paymentViewModel.makePayment(context)

            paymentIntentClientSecret = result.paymentIntent

            customerConfig = PaymentSheet.CustomerConfiguration(
                result.customer,
                result.ephemeralKey
            )

            val publishableKey = result.publishableKey

            PaymentConfiguration.init(context, publishableKey)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // Photo Picker
        Column (
            modifier = Modifier.fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GradientButton(
                text = "Pick An Image",
                gradient = greenGradient,
                icon = Icons.Outlined.AddPhotoAlternate,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp),
                onClick = {
                    photoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }

            )
            Spacer(modifier = Modifier.height(10.dp))

            AsyncImage(
                model = uri,
                contentDescription = "Selected Image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
            )

            // Check Image Button
            Button(
                onClick = {
                    scope.launch {
                        val file = File(context.cacheDir, "uploadImage.jpg")

                        withContext(Dispatchers.IO) {
                            file.createNewFile()
                        }

                        uri?.let {
                            context.contentResolver.openInputStream(it)?.use {input ->
                                file.outputStream().use { output->
                                    input.copyTo(output)
                                }
                            }
                        }

                        Log.d("EKYC", "DONE FILE")

                        val hashCode =  paymentViewModel.uploadImg(file =file)

                        Log.d("EKYC", hashCode)
                    }
                }
            ) {
                Text(text = "Upload Image")
            }

            // Check Liveness
            Button(
                onClick = {
                    scope.launch {
                        val hashcode =  "idg20240108-0e5a6ef3-ac31-0b3e-e063-62199f0a248a/IDG01_1f20035a-ae37-11ee-a428-7df83c107f51"
                        paymentViewModel.checkLiveness("idg20240108-0e5a6ef3-ac31-0b3e-e063-62199f0a248a/IDG01_447d2db9-ae38-11ee-ba09-87b45b3fabbb")
                    }
                }
            ) {
                Text(text = "Check Liveness")
            }

            // Payment Butotn
            Button(
                onClick = {
                    val currentConfig = customerConfig
                    val currentClientSecret = paymentIntentClientSecret

                    if (currentConfig != null && currentClientSecret != null) {
                        presentPaymentSheet(paymentSheet, currentConfig, currentClientSecret)
                    }
                }
            ) {
                Text(text = "Confirm payment")
            }
        }


    }

    LaunchedEffect(key1 = paymentViewModel.isPaymentCompleted.value) {
        scope.launch {
            if (paymentViewModel.isPaymentCompleted.value) {
                Toast.makeText(context, paymentViewModel.isPaymentCompleted.value.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}

private fun presentPaymentSheet(
    paymentSheet: PaymentSheet,
    customerConfig: PaymentSheet.CustomerConfiguration,
    paymentIntentClientSecret: String
) {
    paymentSheet.presentWithPaymentIntent(
        paymentIntentClientSecret,
        PaymentSheet.Configuration(
            merchantDisplayName = "My merchant name",
            customer = customerConfig,
            // Set `allowsDelayedPaymentMethods` to true if your business handles
            // delayed notification payment methods like US bank accounts.
            allowsDelayedPaymentMethods = true
        )
    )
}

//fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
//    when(paymentSheetResult) {
//        is PaymentSheetResult.Canceled -> {
//            Log.d("StripeAPI", "Canceled")
//        }
//
//        is PaymentSheetResult.Failed -> {
//            Log.d("StripeAPI", "Error: ${paymentSheetResult.error.message}")
//        }
//
//        is PaymentSheetResult.Completed -> {
//            Log.d("StripeAPI", "Completed")
//
////            Toast(LocalContext.current)
//        }
//    }
//}