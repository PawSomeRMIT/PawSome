package com.example.pawsome.presentation.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.ManageHistory
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pawsome.R
import com.example.pawsome.data.login.LoginUIEvent
import com.example.pawsome.domain.Graph
import com.example.pawsome.domain.screens.Screen
import com.example.pawsome.domain.screens.SettingScreen
import com.example.pawsome.model.Booking
import com.example.pawsome.model.User
import com.example.pawsome.presentation.authentication.components.ButtonComponent
import com.example.pawsome.presentation.authentication.login.LoginViewModel
import com.example.pawsome.presentation.settings.components.Profile
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.rememberPaymentSheet
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SettingScreen(
    navController: NavHostController,
    rootNavController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    val auth = FirebaseAuth.getInstance()
    val userID = auth.currentUser?.uid
    var userData by remember(userID) { mutableStateOf(User()) }

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    // Stripe payment
    val paymentSheet = rememberPaymentSheet(settingViewModel::onPaymentSheetResult)

    var customerConfig by remember {
        mutableStateOf<PaymentSheet.CustomerConfiguration?>(null)
    }

    var paymentIntentClientSecret by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(userID) {
        if (userID != null) {
            val userRef = Firebase.firestore.collection("user").document(userID)
            val snapshot = userRef.get().await()

            snapshot?.let {
                val result = User(
                    userID = it.get("userID").toString(),
                    username = it.get("username").toString(),
                    email = it.get("email").toString(),
                    image = it.get("image").toString(),
                    membership = it.get("membership").toString(),
                    chatToken = it.get("chatToken").toString(),
                    history = it.get("history") as List<Booking>
                )

                userData = result
            }
        }
    }

    LaunchedEffect(key1 = context) {
        scope.launch {
            val result = settingViewModel.makePayment(19.99)

            paymentIntentClientSecret = result.paymentIntent

            customerConfig = PaymentSheet.CustomerConfiguration(
                result.customer,
                result.ephemeralKey
            )

            val publishableKey = result.publishableKey

            PaymentConfiguration.init(context, publishableKey)
        }
    }

    Surface(color = Color.White,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
            Profile(
                userData = userData
            )

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))

            if (userData.membership == "normal") {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val currentConfig = customerConfig
                            val currentClientSecret = paymentIntentClientSecret

                            if (currentConfig != null && currentClientSecret != null) {
                                presentPaymentSheet(
                                    paymentSheet,
                                    currentConfig,
                                    currentClientSecret
                                )
                            }
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ManageAccounts,
                        contentDescription = "Icon description",
                        modifier = Modifier.size(40.dp),
                        tint = colorResource(id = R.color.gray_800)
                    )

                    Spacer(modifier = Modifier.width(40.dp))

                    Text(
                        text = "Upgrade Account",
                        color = colorResource(id = R.color.gray_800),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 25.sp,
                    )
                }

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp))
            }

            // About US
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(SettingScreen.BookingHistory.route)
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.ManageHistory,
                    contentDescription = "Icon description",
                    modifier = Modifier.size(40.dp),
                    tint = colorResource(id = R.color.gray_800)
                )

                Spacer(modifier = Modifier.width(40.dp))

                Text(
                    text = "Booking History",
                    color = colorResource(id = R.color.gray_800),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                )
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(40.dp))

            // Uploaded pets
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "user",
                            userData
                        )

                        navController.navigate(SettingScreen.MyPetList.route)
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Pets,
                    contentDescription = "Icon description",
                    modifier = Modifier.size(40.dp),
                    tint = colorResource(id = R.color.gray_800)
                )

                Spacer(modifier = Modifier.width(40.dp))

                Text(
                    text = "My Pets",
                    color = colorResource(id = R.color.gray_800),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                )
            }


            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(40.dp))

            ButtonComponent(
                value = "Sign out",
                modifier = Modifier
                    .height(48.dp)
                    .padding(horizontal = 40.dp),
                onButtonClicked = {
                    scope.launch {
                        // Sign out the current user
                        loginViewModel.onEvent(LoginUIEvent.LogoutButtonClicked)

                        // Navigate back to the Login screen
                        rootNavController.navigate(Screen.Register.Login.route) {
                            popUpTo(Screen.Register.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                },
                isEnabled = true
            )
        }
    }

    LaunchedEffect(key1 = settingViewModel.isPaymentCompleted.value) {
        scope.launch {
            if (settingViewModel.isPaymentCompleted.value) {
                Toast.makeText(context, "Payment Completed!", Toast.LENGTH_SHORT).show()

                settingViewModel.upgradeAccount()

                navController.navigate(Graph.PETSLIST) {
                    popUpTo(Graph.PETSLIST) {
                        inclusive = true
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
