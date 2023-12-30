/*
    RMIT University Vietnam
    Course: COSC2657 Android Development
    Semester: 2023C
    Assessment: Assignment 2
    Author: Lai Nghiep Tri
    ID: s3799602
    Created  date: 19/12/2023
    Last modified: 20/12/2023
    Acknowledgement: Figma UI, Android Developer documentation, Firebase Documentation, etc
 */

package com.example.pawsome.common
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.pawsome.data.DataViewModel
import com.example.pawsome.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

@Composable
fun NavBar(navController: NavController) {
    BottomAppBar(
        cutoutShape = MaterialTheme.shapes.small.copy(
            androidx.compose.foundation.shape.CornerSize(
                percent = 50
            )
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        backgroundColor = Color.White
    ) {
        IconButton(onClick = { navController.navigate("home") }) {
            Icon(Icons.Outlined.Home, contentDescription = "Home")

        }
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = { navController.navigate("maps") }) {
            Icon(Icons.Outlined.Map, contentDescription = "Maps")
        }
        Spacer(Modifier.weight(1f, true))
        Box(Modifier.size(48.dp)) // Placeholder for the FAB
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = { navController.navigate("aboutus") }) {
            Icon(Icons.Outlined.Info, contentDescription = "About US")

        }
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = { navController.navigate("settings") }) {
            Icon(Icons.Outlined.Settings, contentDescription = "Settings")
        }
    }
}

@Composable
fun CenterActionButton(navController: NavController,
                       dataViewModel: DataViewModel = viewModel()) {
    var userRole: String = ""
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val userID = auth.currentUser?.uid
    var userData by remember(userID) {
        mutableStateOf(User())
    }

    LaunchedEffect(userID) {
        if (userID != null) {
            val userRef = Firebase.firestore.collection("user").document(userID)
            val snapshot = userRef.get().await()

            snapshot?.let {
                snapshot.toObject<User>()?.let {
                    userData = it
                    userRole = it.userType
                }
            }
        }
    }


    FloatingActionButton(onClick = {
        navController.navigate("addBooking")
    }) {
        Icon(Icons.Outlined.Add, contentDescription = "Add Booking")
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { /* ... */ }
        composable("profile") { /* ... */ }
        composable("favorites") { /* ... */ }
        composable("settings") { /* ... */ }
        composable("refresh") { /* ... */ }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun NavPreview() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { NavBar(navController = navController) },
        floatingActionButton = { CenterActionButton(navController = navController) },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ){}
}