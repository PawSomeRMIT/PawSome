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

package com.example.pawsome.presentation.authentication.register


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pawsome.data.Validator
import com.example.pawsome.data.repository.AuthRepo
import com.example.pawsome.data.repository.BackEndRepo
import com.example.pawsome.data.signup.SignUpState
import com.example.pawsome.data.signup.SignupUIEvent
import com.example.pawsome.data.signup.SignupUIState
import com.example.pawsome.model.User
import com.example.pawsome.model.api_model.RequestBody
import com.example.pawsome.util.Resource
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val backEndRepo: BackEndRepo
) : ViewModel() {
    private val tag = SignupViewModel::class.simpleName
    var signupUIState = mutableStateOf(SignupUIState())
    var allValidationsPassed = mutableStateOf(false)
    var signUpInProgress = mutableStateOf(false)

    private val _signUpState = Channel<SignUpState>()
    val signUpState = _signUpState.receiveAsFlow()

    suspend fun onEvent(event: SignupUIEvent) {
        when (event) {
            is SignupUIEvent.UsernameChanged -> {
                signupUIState.value = signupUIState.value.copy(
                    username = event.username
                )
                printState()
            }

            is SignupUIEvent.EmailChanged -> {
                signupUIState.value = signupUIState.value.copy(
                    email = event.email
                )
                printState()
            }

            is SignupUIEvent.PasswordChanged -> {
                signupUIState.value = signupUIState.value.copy(
                    password = event.password
                )
                printState()
            }

            is SignupUIEvent.ConfirmedPasswordChanged -> {
                signupUIState.value = signupUIState.value.copy(
                    confirmedPassword = event.confirmedPassword
                )
                printState()
            }

            is SignupUIEvent.RegisterButtonClicked -> {
                signUp()
                Log.d("Inside OnEvent", "Finish SignUp")
            }
        }
        validateDataWithRules()
    }


    private suspend fun signUp() {
        Log.d(tag, "Inside_signUp")
        printState()

        val email = signupUIState.value.email
        val userName = signupUIState.value.username

        Log.d("Before signup", userName)

        authRepo.registerUser(email = email, password = signupUIState.value.password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d("Register response", result.data?.user?.uid.toString())

                    // Call back end to create chat user
                    val requestBody = RequestBody(userId = email.split("@")[0], username = userName)

                    val response = backEndRepo.create_user(requestBody)

                    val token = response.response

                    Log.d("api respone", response.toString())

                    saveUserData(
                        userID = result.data?.user?.uid.toString(),
                        username = userName,
                        email = email,
                        chatToken = token
                        )

                    _signUpState.send(SignUpState(isSuccess = "Register Successfully!"))
                }

                is Resource.Loading -> {
                    Log.d("Register response", result.toString())
                    _signUpState.send(SignUpState(isLoading = true))
                }

                is Resource.Error -> {
                    Log.d("Register response", result.toString())
                    _signUpState.send(SignUpState(isError = result.message))
                }
            }
        }
    }

    private fun validateDataWithRules() {

        val usernameResult = Validator.validateUsername(
            username = signupUIState.value.username
        )

        val emailResult = Validator.validateEmail(
            email = signupUIState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = signupUIState.value.password
        )

        val confirmedPasswordResult = Validator.validateConfirmedPassword(
            confirmedPassword = signupUIState.value.confirmedPassword,
            password = signupUIState.value.password
        )

        Log.d(tag, "Inside_validateDataWithRules")
        Log.d(tag, "emailResult= $emailResult")
        Log.d(tag, "passwordResult= $passwordResult")
        Log.d(tag, "confirmedPasswordResult= $confirmedPasswordResult")
        Log.d(tag, "usernameResult= $usernameResult")

        signupUIState.value = signupUIState.value.copy(
            emailError = emailResult.status,
            usernameError = emailResult.status,
            passwordError = passwordResult.status,
            confirmedPasswordError = confirmedPasswordResult.status
        )

        allValidationsPassed.value =
                emailResult.status && passwordResult.status &&
                usernameResult.status && confirmedPasswordResult.status
    }

    private fun printState() {
        Log.d(tag, "Inside_printState")
        Log.d(tag, signupUIState.value.toString())
    }
}

fun saveUserData(
    userID: String,
    username: String,
    email: String,
    image: String = "https://i.postimg.cc/Kj1LjWRN/charles-leclerc.png",
    membership: String = "normal",
    chatToken: String
) {
    val tag = "Data save"

    val userData = User(
        userID = userID,
        username = username,
        email = email,
        image = image,
        membership = membership,
        chatToken = chatToken
    )

    val userRef = Firebase.firestore.collection("user").document(userID)

    userRef
        .set(userData)
        .addOnSuccessListener {
            Log.d(tag, "Successfully store user")
        }
        .addOnFailureListener {
            Log.d(tag, "Inside_OnFailureListener")
            Log.d(tag, "Exception= ${it.message}")
            Log.d(tag, "Exception= ${it.localizedMessage}")
        }
}