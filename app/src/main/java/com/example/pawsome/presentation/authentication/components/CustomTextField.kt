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


package com.example.pawsome.presentation.authentication.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.pawsome.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    labelValue: String,
    painterResource: Painter,
    onTextChanged: (String) -> Unit,
    errorStatus: Boolean = false,
    color: TextFieldColors? = null,
    readOnly: Boolean? = null,
    modifier: Modifier? = null,
    trailingIcon: @Composable (() -> Unit)? = null) {

    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        label = { Text(text = labelValue)},
        value = textValue.value,
        modifier = modifier?: Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(2.dp)),
        colors = color
            ?: TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = Color(0,136,204),
                focusedBorderColor = Color(0,136,204),
                cursorColor = Color(0,136,204),
            ),
        // Enter to move to next field
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        onValueChange = { it:String ->
            textValue.value = it
            onTextChanged(it)
        },
        isError = !errorStatus,
        readOnly = readOnly?: false,
        leadingIcon = {
            Icon(
                painter = painterResource,
                contentDescription = "Person icon"
            )
        },
        trailingIcon = trailingIcon
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    labelValue: String,
    painterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    val localFocus = LocalFocusManager.current

    val password = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        label = { Text(text = labelValue)},
        value = password.value,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(2.dp)),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = Color(0,136,204),
            focusedBorderColor = Color(0,136,204),
            cursorColor = Color(0,136,204),
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions {
            localFocus.clearFocus()
        },
        onValueChange = { it:String ->
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource,
                contentDescription = "Person icon")},
        trailingIcon = {
            val iconImage = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            var description = if (passwordVisible.value) R.string.hide_password else R.string.show_password
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = iconImage, contentDescription = "Password visibility")
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = !errorStatus
    )
}
