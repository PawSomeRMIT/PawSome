package com.example.pawsome.presentation.detailscreen.components
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PriceAdoptButton(price: Float, onAdoptClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth().height(80.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$ $price",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = onAdoptClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFE0B2)), // Creamy background color
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            shape = RoundedCornerShape(15) // Fully rounded corners
        ) {
            Text(
                text = "Adopt Me",
                color = Color(0xFFF57C00), // Button text color
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPriceAdoptButton() {
    MaterialTheme {
        PriceAdoptButton(price = 120f) {}
    }
}
