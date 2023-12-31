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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pawsome.R
import com.example.pawsome.data.signup.SignupUIEvent
import com.example.pawsome.data.signup.SignupViewModel
import com.example.pawsome.data.signup.SignupViewModelFactory
import com.example.pawsome.presentation.authentication.components.ButtonComponent
import com.example.pawsome.presentation.authentication.components.CustomTextField
import com.example.pawsome.presentation.authentication.components.DividerComponent
import com.example.pawsome.presentation.authentication.components.PasswordTextField
import com.example.pawsome.common.NormalText
import com.example.pawsome.common.TitleText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Signup(
    navHostController: NavHostController
) {
    var isExpanded by remember { mutableStateOf(false) }

    var signupViewModel: SignupViewModel = viewModel(factory = SignupViewModelFactory(navHostController = navHostController))

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

                Image(painter = painterResource(id = R.drawable.signup_background),
                    contentDescription = "Signup background",
                    modifier = Modifier.fillMaxWidth())
                TitleText(value = stringResource(id = R.string.signup_title))

                Row {
                    CustomTextField(
                        labelValue = stringResource(id = R.string.first_name),
                        painterResource(id = R.drawable.baseline_person_24),
                        onTextChanged = {
                            signupViewModel.onEvent(SignupUIEvent.FirstNameChanged(it))
                        },
                        modifier = Modifier.width(170.dp),
                        errorStatus = signupViewModel.signupUIState.value.firstNameError
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    CustomTextField(
                        labelValue = stringResource(id = R.string.last_name),
                        painterResource(id = R.drawable.baseline_person_24),
                        onTextChanged = {
                            signupViewModel.onEvent(SignupUIEvent.LastNameChanged(it))
                        },
                        modifier = Modifier.width(170.dp),
                        errorStatus = signupViewModel.signupUIState.value.lastNameError
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    CustomTextField(
                        labelValue = stringResource(id = R.string.username_field),
                        painterResource(id = R.drawable.baseline_person_24),
                        onTextChanged = {
                            signupViewModel.onEvent(SignupUIEvent.UsernameChanged(it))
                        },
                        modifier = Modifier.width(170.dp),
                        errorStatus = signupViewModel.signupUIState.value.usernameError
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Box(
                        modifier = Modifier.width(200.dp)
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = isExpanded,
                            onExpandedChange = { isExpanded = it }
                        ) {
                            CustomTextField(
                                labelValue = signupViewModel.signupUIState.value.userType,
                                painterResource(id = R.drawable.baseline_shield_24),
                                onTextChanged = {
                                    signupViewModel.onEvent(SignupUIEvent.UserTypeChanged(it))
                                },
                                errorStatus = signupViewModel.signupUIState.value.userTypeError,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                                },
                                readOnly = true,
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp)),
                            )

                            ExposedDropdownMenu(
                                expanded = isExpanded,
                                onDismissRequest = { isExpanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text(text = "Normal") },
                                    onClick = {
                                        signupViewModel.onEvent(SignupUIEvent.UserTypeChanged("Normal"))
                                        isExpanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text(text = "Admin") },
                                    onClick = {
                                        signupViewModel.onEvent(SignupUIEvent.UserTypeChanged("Admin"))
                                        isExpanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text(text = "Super") },
                                    onClick = {
                                        signupViewModel.onEvent(SignupUIEvent.UserTypeChanged("Super"))
                                        isExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                CustomTextField(
                    labelValue = stringResource(id = R.string.email),
                    painterResource(id = R.drawable.baseline_alternate_email_24),
                    onTextChanged = {
                        signupViewModel.onEvent(SignupUIEvent.EmailChanged(it))
                    },
                    errorStatus = signupViewModel.signupUIState.value.emailError
                )

                Spacer(modifier = Modifier.height(10.dp))


                PasswordTextField(labelValue = stringResource(id = R.string.password),
                    painterResource(id = R.drawable.baseline_lock_24),
                    onTextSelected = {
                        signupViewModel.onEvent(SignupUIEvent.PasswordChanged(it))
                    },
                    errorStatus = signupViewModel.signupUIState.value.passwordError
                )

                Spacer(modifier = Modifier.height(20.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.register),
                    onButtonClicked = {
                        signupViewModel.onEvent(SignupUIEvent.RegisterButtonClicked)
                    },
                    isEnabled = signupViewModel.allValidationsPassed.value
                )

                DividerComponent()

                val annotatedString = buildAnnotatedString {
                    append("Already have an account? ")
                    withStyle(style = SpanStyle(
                        color = Color(49,109,246),
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
    }
}