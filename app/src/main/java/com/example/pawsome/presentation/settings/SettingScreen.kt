package com.example.pawsome.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pawsome.data.login.LoginUIEvent
import com.example.pawsome.data.login.LoginViewModel
import com.example.pawsome.domain.screens.Screen
import com.example.pawsome.model.Booking
import com.example.pawsome.model.User
import com.example.pawsome.presentation.authentication.components.ButtonComponent
import com.example.pawsome.presentation.settings.components.Profile
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SettingScreen(
    navController: NavHostController,
    rootNavController: NavHostController,
    loginViewModel: LoginViewModel= hiltViewModel()
) {
    val auth = FirebaseAuth.getInstance()
    val userID = auth.currentUser?.uid
    var userData by remember(userID) { mutableStateOf(User()) }

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

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

    Surface(color = Color.White,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Profile(
                inputImageUrl = userData.image,
                name = userData.username,
                tier = userData.membership)

            ButtonComponent(
                value = "Sign out",
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
}
