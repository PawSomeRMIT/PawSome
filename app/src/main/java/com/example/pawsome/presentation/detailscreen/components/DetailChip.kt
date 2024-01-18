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

package com.example.pawsome.presentation.detailscreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawsome.R

@Composable
fun DetailChip(
    title: String,
    info: String
) {
    Surface(
        modifier = Modifier.padding(5.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(20), // This creates a rounded corner with a very high radius, making it circular
        border = BorderStroke(1.dp, colorResource(id = R.color.yellow))
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            Text(
                text = info,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.yellow)
            )

        }

    }
}
