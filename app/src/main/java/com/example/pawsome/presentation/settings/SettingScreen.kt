package com.example.pawsome.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pawsome.common.NormalText
import com.example.pawsome.data.login.LoginUIEvent
import com.example.pawsome.data.login.LoginViewModel
import com.example.pawsome.data.login.LoginViewModelFactory
import com.example.pawsome.model.User
import com.example.pawsome.presentation.authentication.components.ButtonComponent
import com.example.pawsome.presentation.settings.components.Profile
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

@Composable
fun SettingScreen(
    navController: NavHostController
) {

    val auth = FirebaseAuth.getInstance()

    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(navHostController = navController))

    val userID = auth.currentUser?.uid
    var userData by remember(userID) { mutableStateOf(User()) }

    LaunchedEffect(userID) {
        if (userID != null) {
            val userRef = Firebase.firestore.collection("user").document(userID)
            val snapshot = userRef.get().await()

            snapshot?.let {
                snapshot.toObject<User>()?.let {
                    userData = it
                }
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
                name = "${userData.firstname} ${userData.lastname}",
                username = userData.username,
                tier = userData.userType)

            ButtonComponent(
                value = "Sign out",
                onButtonClicked = { loginViewModel.onEvent(LoginUIEvent.LogoutButtonClicked) },
                isEnabled = true
            )
        }
    }
}
