/*
    RMIT University Vietnam
    Course: COSC2657 Android Development
    Semester: 2023C
    Assessment: Assignment 3
    Author:
        Thieu Tran Tri Thuc - s3870730
        Lai Nghiep Tri - s3799602
        Bui Minh Nhat - s3878174
        Phan Bao Kim Ngan - s3914582
    Created  date: 1/1/2024
    Last modified: 19/1/2024
    Acknowledgement: Figma UI, Android Developer documentation, Firebase Documentation, etc
 */

package com.example.pawsome.presentation.authentication.login


import android.annotation.SuppressLint
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawsome.data.Validator
import com.example.pawsome.data.login.LoginUIEvent
import com.example.pawsome.data.login.LoginUIState
import com.example.pawsome.data.login.SignInState
import com.example.pawsome.data.repository.AuthRepo
import com.example.pawsome.model.Booking
import com.example.pawsome.model.User
import com.example.pawsome.util.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor (
    private val authRepo: AuthRepo,
    private val client: ChatClient
) : ViewModel() {
    var loginUIState = mutableStateOf(LoginUIState())
    var allValidationsPassed = mutableStateOf(false)
    private var loginInProgress = mutableStateOf(false)

    private val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()

    @RequiresApi(Build.VERSION_CODES.Q)
    val permissions = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
//        android.Manifest.permission.POST_NOTIFICATIONS,
    )

    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationCallback: LocationCallback
    var locationRequired: Boolean = false

    @SuppressLint("MissingPermission")
    fun startLocationUpdate() {
        Log.d("KEOY", "Inside startLocationUpdate")

        locationCallback?.let {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 100
            )
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(3000)
                .setMaxUpdateDelayMillis(100)
                .build()

            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    suspend fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> { login() }
            is LoginUIEvent.LogoutButtonClicked -> { logout() }
        }

        validateLoginUIDataWithRules()
    }

    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidationsPassed.value = emailResult.status && passwordResult.status
    }

    private suspend fun login() {
        loginInProgress.value = true
        val email = loginUIState.value.email
        val password = loginUIState.value.password

        authRepo.loginUser(email = email, password = password).collect{result ->
            when (result) {
                is Resource.Success -> {
                    viewModelScope.launch {
                        val user = getUserFromFireStore(email = email)

                        Log.d("Got user", user.toString())

                        loginChatUser(user)

                        _signInState.send(SignInState(isSuccess = "Sign In Successfully!"))
                    }
                }

                is Resource.Loading -> {
                    _signInState.send(SignInState(isLoading = true))
                }

                is Resource.Error -> {
                    _signInState.send(SignInState(isError = result.message))
                }
            }
        }
    }

    private fun loginChatUser(user: User) {
        Log.d("Got User", "Enter loginchat")

        val chatUser = io.getstream.chat.android.models.User(
            id = user.email.split("@")[0],
            name = user.username
        )

        Log.d("ABCD", user.username)
        Log.d("ABCD", chatUser.toString())

        client.connectUser(
            user = chatUser,
            token = user.chatToken
        )
            .enqueue { result ->
                if (result.isSuccess) {
                    Log.d("Thuc", result.toString())
                } else {
                    Log.d("Thuc", result.toString())
                }
            }
    }


    private fun logout() {
        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signOut()

        client.disconnect(flushPersistence = false)
            .enqueue {result ->
                if (result.isSuccess) {
                    Log.d("Thuc", result.toString())
                } else {
                    Log.d("Thuc", result.toString())
                }
            }

        // Check if sign out success
        val authStateListener = FirebaseAuth.AuthStateListener {

            // Sign out success
            if (it.currentUser == null) {

                Log.d("Auth", "Sign out success")

            } else {
                Log.d("Auth", "Sign out fail")
            }
        }

        firebaseAuth.addAuthStateListener { authStateListener }
    }
}
suspend fun getUserFromFireStore(email: String) : User {
    val db = FirebaseFirestore.getInstance()
    var user = User()

    try {
        db.collection("user").whereEqualTo("email", email).get().await().map {
            val result = User(
                userID = it.get("userID").toString(),
                username = it.get("username").toString(),
                email = it.get("email").toString(),
                image = it.get("image").toString(),
                membership = it.get("membership").toString(),
                chatToken = it.get("chatToken").toString(),
                history = it.get("history") as List<Booking>
            )

            user = result
        }
    }
    catch (e: FirebaseFirestoreException) {
        Log.d("error", "getDataFromFireStore: $e")
    }

    Log.d("Fetched", user.toString())

    return user
}
