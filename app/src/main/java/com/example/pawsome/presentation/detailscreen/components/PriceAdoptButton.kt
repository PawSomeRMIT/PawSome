package com.example.pawsome.presentation.detailscreen.components
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
fun PriceAdoptButton(price: Double, onAdoptClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$ $price",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.yellow)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = onAdoptClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.yellow)), // Creamy background color
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            shape = RoundedCornerShape(15) // Fully rounded corners
        ) {
            Text(
                text = "Adopt Me",
                color = Color.White, // Button text color
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}
