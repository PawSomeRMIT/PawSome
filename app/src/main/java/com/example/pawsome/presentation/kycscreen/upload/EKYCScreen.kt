package drawable

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material.icons.outlined.FactCheck
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pawsome.R
import com.example.pawsome.common.ColorButton
import com.example.pawsome.domain.ChatScreen
import com.example.pawsome.domain.Graph
import com.example.pawsome.model.PetDetail
import com.example.pawsome.presentation.kycscreen.upload.EKYCScreenViewModel
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.rememberPaymentSheet
import kotlinx.coroutines.launch

//import com.example.pawsome.viewmodel.CameraViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EKYCUploadScreen(
    navHostController: NavHostController,
    petDetail: PetDetail,
    ekycScreenViewModel: EKYCScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    // Launch gallery intent
    var uriFront by remember{
        mutableStateOf<Uri?>(Uri.parse(""))
    }

    val photoPicker1 = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uriFront = it
        }
    )

    // Launch gallery intent
    var uriBack by remember{
        mutableStateOf<Uri?>(Uri.parse(""))
    }

    val photoPicker2 = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uriBack = it
        }
    )

    // Check data loading
    val loadingState by ekycScreenViewModel.isLoading.collectAsState(initial = false)

    val idName by ekycScreenViewModel.idName.collectAsState()

    val idNumber by ekycScreenViewModel.idNumber.collectAsState()

    // Stipe payment
    val paymentSheet = rememberPaymentSheet(ekycScreenViewModel::onPaymentSheetResult)

    var customerConfig by remember {
        mutableStateOf<PaymentSheet.CustomerConfiguration?>(null)
    }

    var paymentIntentClientSecret by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(key1 = context) {
        scope.launch {
            val result = ekycScreenViewModel.makePayment(context, petDetail)

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
    ) {
        IconButton(
            onClick = {
                navHostController.popBackStack()
            }
        ) {
            Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "Back", tint = Color.Black)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (uriFront.toString().isNotBlank() || uriBack.toString().isNotBlank()) {
                // Display the selected or captured image
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = uriFront,
                        contentDescription = "Captured or selected image",
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp)
                    )

                    AsyncImage(
                        model = uriBack,
                        contentDescription = "Captured or selected image",
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp)
                    )
                }
            } else {
                // Display the placeholder image
                Image(
                    painterResource(R.drawable.background_10),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Upload Your ID Card",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Instruction
            if (uriFront.toString().isBlank() && uriBack.toString().isBlank()) {
                Text(
                    text = "Please upload two photos of your ID card (front and back face). The area of the ID card in the picture needs to occupy a minimum of 1/3 and a maximum of 4/5 of the total area of the photo. Please ensure that there is only one photo of your ID card/CCCD in the photo, with no other documents, including documents with text. The photo must have a complete ID card/CCCD with no missing edges. Also, please ensure that the photos are not blurry or glare.",
                    color = colorResource(id = R.color.text_gray),
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Buttons for taking photo and selecting from gallery
            ColorButton(
                text = "Pick Photo of Front side",
                color = colorResource(id = R.color.yellow),
                icon = Icons.Outlined.AddPhotoAlternate,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp),
                onClick = {
                    photoPicker1.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }

            )

            ColorButton(
                text = "Pick Photo of Back side",
                color = colorResource(id = R.color.yellow),
                icon = Icons.Outlined.AddPhotoAlternate,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp),
                onClick = {
                    photoPicker2.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },

                )

            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                modifier = Modifier.padding(horizontal = 25.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            ColorButton(
                text = "Verify ID Card",
                color = colorResource(id = R.color.yellow),
                icon = Icons.Outlined.FactCheck,
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 22.dp),
                onClick = {
                    scope.launch {
                        if (uriFront != null && uriBack != null) {
                            ekycScreenViewModel.uploadImg(context = context, uriFront = uriFront!!, uriBack = uriBack!!)
                        }
                    }
                },
                isEnable = (uriFront.toString().isNotBlank() && uriBack.toString().isNotBlank())
            )

            if (ekycScreenViewModel.isVeificationCompleted.value) {
                TextField(
                    value = idNumber,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    placeholder = {
                        Text(text = "Search")
                    },
                    shape = RoundedCornerShape(40.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        containerColor = colorResource(id = R.color.light_gray),
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Numbers, "",
                            tint = Color.Black
                        )
                    },
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = idName,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    placeholder = {
                        Text(text = "Search")
                    },
                    shape = RoundedCornerShape(40.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        containerColor = colorResource(id = R.color.light_gray),
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.PermIdentity, "",
                            tint = Color.Black
                        )
                    },
                )

                Spacer(modifier = Modifier.height(10.dp))

                ColorButton(
                    text = "Complete the payment",
                    color = colorResource(id = R.color.yellow),
                    icon = Icons.Outlined.Payment,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 22.dp),
                    onClick = {
                        Log.d("PAYMENT", "Entered")
                        val currentConfig = customerConfig
                        val currentClientSecret = paymentIntentClientSecret

                        if (currentConfig != null && currentClientSecret != null) {
                            presentPaymentSheet(paymentSheet, currentConfig, currentClientSecret)
                        }
                    },
                    isEnable = ekycScreenViewModel.isVeificationCompleted.value
                )

                Spacer(modifier = Modifier.height(20.dp))
            }




            if (loadingState) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.yellow)
                )
            }
        }
    }

    LaunchedEffect(key1 = ekycScreenViewModel.isPaymentCompleted.value) {
        scope.launch {
            if (ekycScreenViewModel.isPaymentCompleted.value) {
                Toast.makeText(context, "Payment Completed!", Toast.LENGTH_SHORT).show()

                ekycScreenViewModel.createBooking(petDetail = petDetail)

                navHostController.navigate(Graph.PETSLIST) {
                    popUpTo(0) {
                    }
                }
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