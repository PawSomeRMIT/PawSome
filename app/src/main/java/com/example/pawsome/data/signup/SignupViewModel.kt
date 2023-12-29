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


class SignupViewModel(
    private val navHostController: NavHostController
) : ViewModel() {
    private val TAG = SignupViewModel::class.simpleName
    var signupUIState = mutableStateOf(SignupUIState())
    var allValidationsPassed = mutableStateOf(false)
    var signUpInProgress = mutableStateOf(false)
    fun onEvent(event: SignupUIEvent) {
        when (event) {
            is SignupUIEvent.FirstNameChanged -> {
                signupUIState.value = signupUIState.value.copy(
                    firstName = event.firstname
                )
                printState()
            }

            is SignupUIEvent.LastNameChanged -> {
                signupUIState.value = signupUIState.value.copy(
                    lastName = event.lastname
                )
                printState()
            }

            is SignupUIEvent.UsernameChanged -> {
                signupUIState.value = signupUIState.value.copy(
                    username = event.username
                )
                printState()
            }

            is SignupUIEvent.UserTypeChanged -> {
                signupUIState.value = signupUIState.value.copy(
                    userType = event.userType
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

            is SignupUIEvent.RegisterButtonClicked -> { signUp() }

            else -> {}
        }
        validateDataWithRules()
    }


    private fun signUp() {
        Log.d(TAG, "Inside_signUp")
        printState()
        createUserInFirebase(
            email = signupUIState.value.email,
            password = signupUIState.value.password,
            signupUIState
        )
    }

    private fun validateDataWithRules() {
        val fNameResult = Validator.validateFirstName(
            fName = signupUIState.value.firstName
        )

        val lNameResult = Validator.validateLastName(
            lName = signupUIState.value.lastName
        )

        val usernameResult = Validator.validateUsername(
            username = signupUIState.value.username
        )

        val userTypeResult = Validator.validateUsername(
            username = signupUIState.value.userType
        )

        val emailResult = Validator.validateEmail(
            email = signupUIState.value.email
        )


        val passwordResult = Validator.validatePassword(
            password = signupUIState.value.password
        )

        Log.d(TAG, "Inside_validateDataWithRules")
        Log.d(TAG, "fNameResult= $fNameResult")
        Log.d(TAG, "lNameResult= $lNameResult")
        Log.d(TAG, "emailResult= $emailResult")
        Log.d(TAG, "userTypeResult= $userTypeResult")
        Log.d(TAG, "passwordResult= $passwordResult")
        Log.d(TAG, "usernameResult= $usernameResult")

        signupUIState.value = signupUIState.value.copy(
            firstNameError = fNameResult.status,
            lastNameError = lNameResult.status,
            emailError = emailResult.status,
            userTypeError = userTypeResult.status,
            usernameError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidationsPassed.value = fNameResult.status && lNameResult.status &&
                emailResult.status && passwordResult.status && usernameResult.status
    }

    private fun printState() {
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, signupUIState.value.toString())
    }

    private fun createUserInFirebase(email: String, password: String, others: MutableState<SignupUIState>) {
        signUpInProgress.value = true
        var auth = FirebaseAuth.getInstance()

        auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside_OnCompleteListener")
                Log.d(TAG, " isSuccessful = ${it.isSuccessful}")

                signUpInProgress.value = false
                if (it.isSuccessful) {

                    // Save user data
                    saveUserData(
                        auth.currentUser?.uid ?: "",
                        others.value.lastName,
                        others.value.firstName,
                        others.value.username,
                        others.value.email,
                        others.value.image,
                        others.value.attendActivity,
                        others.value.membership,
                        others.value.userType
                    )

                    // Back to Login page
                    navHostController.navigate(Screen.Register.Login.route)
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Inside_OnFailureListener")
                Log.d(TAG, "Exception= ${it.message}")
                Log.d(TAG, "Exception= ${it.localizedMessage}")
            }
    }
}

fun saveUserData(
    userID: String,
    lastname: String,
    firstname: String,
    username: String,
    email: String,
    image: String,
    attendActivity: Int = 0,
    membership: String = "normal",
    userType: String = "normal"
) {
    val TAG = "Data save"

    val userData = User(userID = userID, firstname = firstname, lastname = lastname, username = username,
        email = email, image = "https://i.postimg.cc/7hjg2wZm/836.jpg", attendActivity = attendActivity,
        membership = membership, userType = userType)

    val userRef = Firebase.firestore.collection("user").document(userID)

    userRef
        .set(userData)
        .addOnSuccessListener {
            Log.d(TAG, "Successfully store user")
        }
        .addOnFailureListener {
            Log.d(TAG, "Inside_OnFailureListener")
            Log.d(TAG, "Exception= ${it.message}")
            Log.d(TAG, "Exception= ${it.localizedMessage}")
        }
}


class SignupViewModelFactory(private val navHostController: NavHostController): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignupViewModel(navHostController = navHostController) as T
    }
}