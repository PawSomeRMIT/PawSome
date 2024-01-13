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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonComponent(
    value: String,
    onButtonClicked: () -> Unit,
    isEnabled: Boolean = false,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(48.dp),
    shape: Shape = RoundedCornerShape(10.dp)) {
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color(232,192,19)),
        shape = shape,
        enabled = isEnabled,
        onClick = { onButtonClicked.invoke() }
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
            .background(
                brush = Brush.horizontalGradient(
                    if (isEnabled) {
                        if (value.lowercase() != "logout" && value.lowercase() != "sign out") {
                            listOf(
                                Color(232,192,19),
                                Color(232,192,19),
                            )
                        } else {
                            listOf(
                                Color.Red,
                                Color.Red
                            )
                        }
                    } else {
                        listOf(
                            Color(227,210,141),
                            Color(227,210,141)
                        )
                    }
                )
            ),
            contentAlignment = Alignment.Center
            ) {

            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color =  Color.White)
        }

    }
}


@Composable
fun TextButton(value: String) {
    Text(text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(40.dp),
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray),
        textAlign = TextAlign.End,
        textDecoration = TextDecoration.Underline
    )
}