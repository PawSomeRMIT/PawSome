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

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pawsome.R
import com.example.pawsome.common.LoadingScreen
import com.example.pawsome.common.TitleText
import com.example.pawsome.data.signup.SignupUIEvent
import com.example.pawsome.data.signup.SignupViewModel
import com.example.pawsome.domain.screens.Screen
import com.example.pawsome.presentation.authentication.components.ButtonComponent
import com.example.pawsome.presentation.authentication.components.CustomTextField
import com.example.pawsome.presentation.authentication.components.DividerComponent
import com.example.pawsome.presentation.authentication.components.PasswordTextField
import kotlinx.coroutines.launch


@Composable
fun Signup(
    navHostController: NavHostController,
    signupViewModel: SignupViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    
    val state = signupViewModel.signUpState.collectAsState(initial = null)

    if (state.value?.isLoading != true) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Surface(color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(20.dp)) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {

                    Image(painter = painterResource(id = R.drawable.background_4),
                        contentDescription = "Signup background",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp))

                    TitleText(value = stringResource(id = R.string.signup_title))

                    Spacer(modifier = Modifier.height(10.dp))

                    CustomTextField(
                        labelValue = stringResource(id = R.string.username_field),
                        painterResource(id = R.drawable.baseline_person_24),
                        onTextChanged = {
                            scope.launch {
                                signupViewModel.onEvent(SignupUIEvent.UsernameChanged(it))
                            }
                        },
                        errorStatus = signupViewModel.signupUIState.value.usernameError
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    CustomTextField(
                        labelValue = stringResource(id = R.string.email),
                        painterResource(id = R.drawable.baseline_email_24),
                        onTextChanged = {
                            scope.launch {
                                signupViewModel.onEvent(SignupUIEvent.EmailChanged(it))
                            }
                        },
                        errorStatus = signupViewModel.signupUIState.value.emailError
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    PasswordTextField(labelValue = stringResource(id = R.string.password),
                        painterResource(id = R.drawable.baseline_lock_24),
                        onTextSelected = {
                            scope.launch {
                                signupViewModel.onEvent(SignupUIEvent.PasswordChanged(it))
                            }
                        },
                        errorStatus = signupViewModel.signupUIState.value.passwordError
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    PasswordTextField(labelValue = stringResource(id = R.string.retyped_password),
                        painterResource(id = R.drawable.baseline_lock_24),
                        onTextSelected = {
                            scope.launch {
                                signupViewModel.onEvent(SignupUIEvent.ConfirmedPasswordChanged(it))
                            }
                        },
                        errorStatus = signupViewModel.signupUIState.value.confirmedPasswordError
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        modifier = Modifier

                            .background(
                                colorResource(id = R.color.light_gray),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(8.dp)
                            .fillMaxWidth(),
                    ) {
                        Text(text = "Account must satisfies following:",
                            color = colorResource(id = R.color.gray),    
                            fontWeight = FontWeight.Bold)
                        
                        Spacer(modifier = Modifier.height(6.dp))
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "1. Password has at least 4 characters.",
                                style = TextStyle(
                                    color = if (!signupViewModel.signupUIState.value.passwordError)
                                        Color.Red else colorResource(
                                        id = R.color.green
                                    ),
                                )
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Icon(imageVector =
                                if (!signupViewModel.signupUIState.value.passwordError)
                                    Icons.Filled.Error else
                                    Icons.Filled.CheckCircle,
                                contentDescription = "Password satisfaction icon",
                                tint = if (!signupViewModel.signupUIState.value.passwordError)
                                    colorResource(id = R.color.red) else
                                    colorResource(id = R.color.green)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "2. Confirmed password matched",
                                style = TextStyle(
                                    color = if (!signupViewModel.signupUIState.value.confirmedPasswordError)
                                        colorResource(id = R.color.red) else
                                        colorResource(id = R.color.green),
                                )
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Icon(imageVector =
                                if (!signupViewModel.signupUIState.value.confirmedPasswordError)
                                    Icons.Filled.Error else
                                    Icons.Filled.CheckCircle,
                                contentDescription = "Confirmed password satisfaction icon",
                                tint = if (!signupViewModel.signupUIState.value.confirmedPasswordError)
                                    colorResource(id = R.color.red) else
                                    colorResource(id = R.color.green)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "3. Username has been filled",
                                style = TextStyle(
                                    color = if (!signupViewModel.signupUIState.value.usernameError)
                                        colorResource(id = R.color.red) else
                                        colorResource(id = R.color.green),
                                )
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Icon(imageVector =
                                if (!signupViewModel.signupUIState.value.usernameError)
                                    Icons.Filled.Error else
                                    Icons.Filled.CheckCircle,
                                contentDescription = "Username satisfaction icon",
                                tint = if (!signupViewModel.signupUIState.value.usernameError)
                                    colorResource(id = R.color.red) else
                                    colorResource(id = R.color.green)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "4. Email has been filled.",
                                style = TextStyle(
                                    color = if (!signupViewModel.signupUIState.value.emailError)
                                        colorResource(id = R.color.red) else
                                        colorResource(id = R.color.green),
                                )
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Icon(imageVector =
                                if (!signupViewModel.signupUIState.value.emailError)
                                    Icons.Filled.Error else
                                    Icons.Filled.CheckCircle,
                                contentDescription = "Email satisfaction icon",
                                tint = if (!signupViewModel.signupUIState.value.emailError)
                                    colorResource(id = R.color.red) else
                                    colorResource(id = R.color.green)
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(20.dp))

                    ButtonComponent(
                        value = stringResource(id = R.string.register),
                        onButtonClicked = {
                            scope.launch {
                                signupViewModel.onEvent(SignupUIEvent.RegisterButtonClicked)
                            }
                        },
                        isEnabled = signupViewModel.allValidationsPassed.value
                    )

                    DividerComponent()

                    val annotatedString = buildAnnotatedString {
                        append("Already have an account? ")
                        withStyle(style = SpanStyle(
                            color = Color(232,192,19),
                            fontWeight = FontWeight.Bold
                        )) {
                            pushStringAnnotation(tag = "Login", annotation = "Login")
                            append("\tLogin")
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
                            navHostController.navigateUp()
                        }
                    )
                }
            }

            if (signupViewModel.signUpInProgress.value)
                CircularProgressIndicator()

            // Toast the successful or error
            Row (
                modifier = Modifier.fillMaxWidth()
            ) {
                LaunchedEffect(key1 = state.value?.isSuccess) {
                    if (state.value?.isSuccess?.isNotEmpty() == true) {
                        val success = state.value?.isSuccess

                        // Announce the success
                        Toast.makeText(context, "$success", Toast.LENGTH_SHORT).show()

                        // Navigate back to the login screen
                        navHostController.navigate(Screen.Register.Login.route) {
                            popUpTo(Screen.LoadingScreen.route) {
                                inclusive = true
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
    }
    else {
        LoadingScreen()
    }
}