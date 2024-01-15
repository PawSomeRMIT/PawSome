package com.example.pawsome.presentation.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
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
import com.example.pawsome.common.ColorButton
import com.example.pawsome.R
import com.example.pawsome.data.login.LoginUIEvent
import com.example.pawsome.data.login.LoginViewModel
import com.example.pawsome.domain.PetsListScreen
import com.example.pawsome.domain.SettingScreen
import com.example.pawsome.domain.screens.Screen
import com.example.pawsome.model.Booking
import com.example.pawsome.model.User
import com.example.pawsome.presentation.authentication.components.ButtonComponent
import com.example.pawsome.presentation.settings.components.Profile
import com.example.pawsome.presentation.settings.components.SquareCard
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
                userData = userData
            )

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(SettingScreen.AboutUs.route)
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Groups,
                    contentDescription = "Icon description",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "About Us",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                )
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(SettingScreen.AboutUs.route)
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Pets,
                    contentDescription = "Icon description",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "My Pets",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                )
            }


            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))

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
