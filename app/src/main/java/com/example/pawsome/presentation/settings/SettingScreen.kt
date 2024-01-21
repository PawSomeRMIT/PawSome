package com.example.pawsome.presentation.settings

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.ManageHistory
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pawsome.R
import com.example.pawsome.data.login.LoginUIEvent
import com.example.pawsome.domain.Graph
import com.example.pawsome.domain.screens.Screen
import com.example.pawsome.domain.screens.SettingScreen
import com.example.pawsome.model.Booking
import com.example.pawsome.model.User
import com.example.pawsome.presentation.authentication.login.LoginViewModel
import com.example.pawsome.presentation.settings.components.Profile
import com.example.pawsome.presentation.settings.components.SquareCard
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
    settingViewModel: SettingViewModel = hiltViewModel(),
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
    ) {
        Image(
            painter = painterResource(id = R.drawable.setting_background),
            contentDescription = "Setting background"
        )

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(120.dp))

            Profile(userData = userData)

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))

            if (userData.membership == "normal") {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Booking history
                    SquareCard(name = "Booking History", icon = Icons.Outlined.ManageHistory) {
                        navController.navigate(SettingScreen.BookingHistory.route)
                    }

                    // Upgrade account
                    SquareCard(name = "Upgrade Account", icon = Icons.Outlined.ManageAccounts) {
                        val currentConfig = customerConfig
                        val currentClientSecret = paymentIntentClientSecret

                        if (currentConfig != null && currentClientSecret != null) {
                            presentPaymentSheet(
                                paymentSheet,
                                currentConfig,
                                currentClientSecret
                            )
                        }
                    }
                }
            } else {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                )  {
                    // Booking history
                    SquareCard(name = "Booking History", icon = Icons.Outlined.ManageHistory) {
                        navController.navigate(SettingScreen.BookingHistory.route)
                    }
                }
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // View my pets
                SquareCard(name = "View my pets", icon = Icons.Outlined.Pets) {
                    navController.currentBackStackEntry?.savedStateHandle?.set("user", userData)
                    navController.navigate(SettingScreen.MyPetList.route)
                }

                // Sign out button
                SquareCard(name = "Log out",
                    icon = Icons.Outlined.Logout,
                    iconColor = R.color.red,
                    textColor = R.color.red,
                    color = R.color.white) {
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
                }
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(120.dp))
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
