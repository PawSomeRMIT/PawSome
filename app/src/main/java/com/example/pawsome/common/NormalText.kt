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

package com.example.pawsome.common
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.pawsome.R

@Composable
fun NormalText(value: String, color: Color =Color.DarkGray, textAlign: TextAlign = TextAlign.Center) {
    Text(text = value,
        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Light),
        color = color,
        textAlign = textAlign
    )
}

@Composable
fun TitleText(value: String, style: String = "bold", color: Int = R.color.black, fontSize: TextUnit = 32.sp) {
    Text(text = value,
        color = colorResource(id = color),
        fontSize = fontSize,
        style = if (style == "bold")
            MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold) else
            MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Light),
        textAlign = TextAlign.Center
    )
}
