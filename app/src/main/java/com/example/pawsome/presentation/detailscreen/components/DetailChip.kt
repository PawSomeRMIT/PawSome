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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawsome.R

@Composable
fun DetailChip(text: String) {
    Surface(
        modifier = Modifier.padding(5.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(50), // This creates a rounded corner with a very high radius, making it circular
        border = BorderStroke(1.dp, colorResource(id = R.color.yellow))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.yellow)
            )
        }
    }
}

//@Preview
//@Composable
//fun PreviewCustomChip() {
//    DetailChip(text = "Sex: Male")
//}
