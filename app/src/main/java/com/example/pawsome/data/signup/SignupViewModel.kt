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


package com.example.pawsome.data.signup


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.pawsome.data.Validator
import com.example.pawsome.model.Screen
import com.example.pawsome.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SignupViewModel(
    private val navHostController: NavHostController
) : ViewModel() {
    private val tag = SignupViewModel::class.simpleName
    var signupUIState = mutableStateOf(SignupUIState())
    var allValidationsPassed = mutableStateOf(false)
    var signUpInProgress = mutableStateOf(false)
    fun onEvent(event: SignupUIEvent) {
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

            is SignupUIEvent.RegisterButtonClicked -> { signUp() }
        }
        validateDataWithRules()
    }


    private fun signUp() {
        Log.d(tag, "Inside_signUp")
        printState()
        createUserInFirebase(
            email = signupUIState.value.email,
            password = signupUIState.value.password,
            signupUIState
        )
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

    private fun createUserInFirebase(email: String, password: String, others: MutableState<SignupUIState>) {
        signUpInProgress.value = true
        val auth = FirebaseAuth.getInstance()

        auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(tag, "Inside_OnCompleteListener")
                Log.d(tag, " isSuccessful = ${it.isSuccessful}")

                signUpInProgress.value = false
                if (it.isSuccessful) {

                    // Save user data
                    saveUserData(
                        auth.currentUser?.uid ?: "",
                        others.value.username,
                        others.value.email,
                        others.value.image,
                        others.value.membership
                    )

                    // Navigate to LoadingScreen
                    navHostController.navigate(Screen.LoadingScreen.route)

                    // Loading for 3 seconds
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(3000) // 3 seconds
                        // Navigate to Login page after delay
                        navHostController.navigate(Screen.Register.Login.route) {
                            popUpTo(Screen.LoadingScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
            .addOnFailureListener {
                Log.d(tag, "Inside_OnFailureListener")
                Log.d(tag, "Exception= ${it.message}")
                Log.d(tag, "Exception= ${it.localizedMessage}")

                val errorType = "Signup Failed"
                val errorDesc = it.localizedMessage

                // Navigate to LoadingScreen
                navHostController.navigate("${Screen.FailureScreen.route}/$errorType/$errorDesc")

                // Loading for 3 seconds
                CoroutineScope(Dispatchers.Main).launch {
                    delay(3000) // 3 seconds
                    // Navigate to signup page after delay
                    navHostController.navigate(Screen.Register.Signup.route) {
                        popUpTo(Screen.FailureScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
    }
}

fun saveUserData(
    userID: String,
    username: String,
    email: String,
    image: String = "https://i.postimg.cc/Kj1LjWRN/charles-leclerc.png",
    membership: String = "normal",
) {
    val tag = "Data save"

    val userData = User(
        userID = userID,
        username = username,
        email = email,
        image = image,
        membership = membership
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


class SignupViewModelFactory(private val navHostController: NavHostController): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignupViewModel(navHostController = navHostController) as T
    }
}