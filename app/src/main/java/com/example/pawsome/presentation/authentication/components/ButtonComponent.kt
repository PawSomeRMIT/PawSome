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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawsome.R

@Composable
fun ButtonComponent(
    value: String,
    onButtonClicked: () -> Unit,
    isEnabled: Boolean = false,
    elevation: ButtonElevation = ButtonDefaults.buttonElevation(5.dp),
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(48.dp),
    shape: Shape = RoundedCornerShape(10.dp)) {
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.yellow)),
        shape = shape,
        elevation = elevation,
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
                                colorResource(id = R.color.yellow),
                                colorResource(id = R.color.yellow)
                            )
                        } else {
                            listOf(
                                colorResource(id = R.color.red),
                                colorResource(id = R.color.red)
                            )
                        }
                    } else {
                        listOf(
                            colorResource(id = R.color.light_yellow),
                            colorResource(id = R.color.light_yellow)
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
