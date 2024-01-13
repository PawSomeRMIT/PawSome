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


package com.example.pawsome.presentation.authentication

import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pawsome.R
import com.example.pawsome.common.LoadingScreen
import com.example.pawsome.common.NormalText
import com.example.pawsome.common.TitleText
import com.example.pawsome.data.login.LoginUIEvent
import com.example.pawsome.data.login.LoginViewModel
import com.example.pawsome.domain.screens.Screen
import com.example.pawsome.model.Booking
import com.example.pawsome.model.User
import com.example.pawsome.presentation.authentication.components.ButtonComponent
import com.example.pawsome.presentation.authentication.components.CustomTextField
import com.example.pawsome.presentation.authentication.components.DividerComponent
import com.example.pawsome.presentation.authentication.components.PasswordTextField
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait

@Composable
fun Login(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var currentLocation by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }

    loginViewModel.fusedLocationClient = LocationServices.getFusedLocationProviderClient(
        context
    )

    loginViewModel.locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            Log.d("KEOY", "Entered location Result")
            super.onLocationResult(p0)

            for (location in p0.locations) {
                currentLocation = LatLng(location.latitude, location.longitude)
            }

            loginViewModel.locationCallback.let {
                loginViewModel.fusedLocationClient.removeLocationUpdates(it)
            }
        }
    }

    val launchMultiplePermissions = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {permissionMap ->
        val areGranted = permissionMap.values.reduce{ acc, next -> acc && next}

        if (areGranted) {
            loginViewModel.locationRequired = true

            Log.d("KEOY", "Start call LocationUpdate")
            loginViewModel.startLocationUpdate()
            Toast.makeText(context, "Permissions Granted", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    val scope = rememberCoroutineScope()

    val state = loginViewModel.signInState.collectAsState(initial = null)

    if (state.value?.isLoading != true) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Surface(color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(28.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {

                    Image(painter = painterResource(
                        id = R.drawable.background_3),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp))

                    Spacer(modifier = Modifier.height(20.dp))

                    TitleText(value = stringResource(id = R.string.login_title))

                    NormalText(value = stringResource(id = R.string.login_desc))

                    Spacer(modifier = Modifier.height(20.dp))

                    CustomTextField(
                        labelValue = stringResource(id = R.string.email),
                        painterResource(id = R.drawable.baseline_email_24),
                        onTextChanged = {
                            scope.launch {
                                loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                            }
                        },
                        errorStatus = loginViewModel.loginUIState.value.emailError
                    )

                    PasswordTextField(
                        labelValue = stringResource(id = R.string.password),
                        painterResource(id = R.drawable.baseline_lock_24),
                        onTextSelected = {
                            scope.launch {
                                loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                            }
                        },
                        errorStatus = loginViewModel.loginUIState.value.passwordError
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    ButtonComponent(
                        value = stringResource(id = R.string.login),
                        onButtonClicked = {
                            scope.launch {
                                loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                            }
                        },
                        isEnabled = loginViewModel.allValidationsPassed.value
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    DividerComponent()

                    Spacer(modifier = Modifier.height(10.dp))


                    val authenText = "New to this app? "
                    val annotatedString = buildAnnotatedString {
                        append(authenText)
                        withStyle(style = SpanStyle(
                            color = Color(49,109,246),
                            fontWeight = FontWeight.Bold
                        )) {
                            pushStringAnnotation(tag = "Signup", annotation = "Signup")
                            append("\tSignup")
                        }
                    }

                    ClickableText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 40.dp),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Normal,
                            textAlign = TextAlign.Center,
                            color = Color(151,151,151)
                        ),
                        text = annotatedString,
                        onClick = {
                            navHostController.navigate(Screen.Register.Signup.route)
                        }
                    )
                }
            }
        }
    }
    else {
        LoadingScreen()
    }

    // Toast the successful or error
    Row (
        modifier = Modifier.fillMaxWidth()
    ) {
        LaunchedEffect(key1 = state.value?.isSuccess) {
            if (state.value?.isSuccess?.isNotEmpty() == true) {
                scope.launch {
                    val success = state.value?.isSuccess

                    // Announce the success
                    Toast.makeText(context, "$success", Toast.LENGTH_SHORT).show()

                    if (loginViewModel.permissions.all {
                            ContextCompat.checkSelfPermission(
                                context,
                                it
                            ) == PackageManager.PERMISSION_GRANTED
                        }) {
                        // Get location
                        loginViewModel.startLocationUpdate()

                    } else {
                        launchMultiplePermissions.launch(loginViewModel.permissions)
                    }
                }
            }
        }

        LaunchedEffect(key1 = currentLocation.latitude) {
            if (currentLocation.latitude != 0.0 && currentLocation.longitude != 0.0) {
                scope.launch {
                    var userData = User()

                    val auth = FirebaseAuth.getInstance()
                    val userID = auth.currentUser?.uid

                    val userRef = userID?.let {
                        Firebase.firestore.collection("user").document(it)
                    }

                    val snapshot = userRef?.get()?.await()

                    snapshot?.let {
//                        snapshot.toObject<User>()?.let {
//                            userData = it
//                        }

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

                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                        "location",
                        currentLocation
                    )

                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                        "user",
                        userData
                    )

                    // Navigate back to the login screen
                    navHostController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.LoadingScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }

        }

        LaunchedEffect(key1 = state.value?.isError) {
            scope.launch {
                if (state.value?.isError?.isNotEmpty() == true) {
                    val error = state.value?.isError
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
