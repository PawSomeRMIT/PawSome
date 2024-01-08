package com.example.pawsome.presentation.detailscreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DetailChip(text: String) {
    Surface(
        modifier = Modifier.padding(4.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(50), // This creates a rounded corner with a very high radius, making it circular
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun PreviewCustomChip() {
    DetailChip(text = "Sex: Male")
}
