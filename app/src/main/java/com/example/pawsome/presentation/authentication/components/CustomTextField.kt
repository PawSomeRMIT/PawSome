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

package com.example.pawsome.presentation.authentication.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.pawsome.R

@Composable
fun CustomTextField(
    labelValue: String,
    painterResource: Painter = painterResource(id = R.drawable.ic_launcher_foreground),
    value: String? = null,
    onTextChanged: (String) -> Unit,
    errorStatus: Boolean = false,
    color: TextFieldColors? = null,
    readOnly: Boolean? = null,
    modifier: Modifier? = null,
    keyboardType: String? = "text",
    lastField: Boolean = false,
    placeholder: String? = "",
    trailingIcon: @Composable (() -> Unit)? = null) {

    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        label = { Text(text = labelValue)},
        value = value ?: textValue.value,
        modifier = modifier?: Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(2.dp)),
        colors = color
            ?: OutlinedTextFieldDefaults.colors(
                cursorColor = colorResource(id = R.color.yellow),
                focusedBorderColor = colorResource(id = R.color.yellow),
                focusedLabelColor = colorResource(id = R.color.yellow),
            ),
        // Enter to move to next field
        keyboardOptions = if (keyboardType == "text") {
            KeyboardOptions(imeAction = if (lastField) ImeAction.Done else ImeAction.Next)
        } else {
            KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = if (lastField) ImeAction.Done else ImeAction.Next)
        },
        singleLine = true,
        maxLines = 1,
        onValueChange = { it:String ->
            textValue.value = it
            onTextChanged(it)
        },
        isError = !errorStatus,
        readOnly = readOnly?: false,
        placeholder = { Text(placeholder?:"") },
        leadingIcon = {
            Icon(
                painter = painterResource,
                contentDescription = "Person icon"
            )
        },
        trailingIcon = trailingIcon
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasswordTextField(
    labelValue: String,
    painterResource: Painter,
    lastField: Boolean = false,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    val localFocus = LocalFocusManager.current

    val keyboardController = LocalSoftwareKeyboardController.current

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
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = colorResource(id = R.color.yellow),
            focusedBorderColor = colorResource(id = R.color.yellow),
            focusedLabelColor = colorResource(id = R.color.yellow),
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = if (lastField) ImeAction.Done else ImeAction.Next),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() },
            onNext = {
                if (!lastField) {
                    localFocus.moveFocus(FocusDirection.Down)
                }
            }
        ),
        onValueChange = { it:String ->
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource,
                contentDescription = "Person icon")},
        trailingIcon = {
            val iconImage = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = iconImage, contentDescription = "Password visibility")
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = !errorStatus
    )
}
