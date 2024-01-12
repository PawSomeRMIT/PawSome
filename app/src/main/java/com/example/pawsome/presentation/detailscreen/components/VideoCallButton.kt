package com.example.pawsome.presentation.detailscreen.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pawsome.R

@Composable
fun VideoCallButton(onClick: () -> Unit) {
    Surface(
        modifier = Modifier.size(56.dp),
        color = colorResource(id = R.color.yellow), // Replace with your desired background color
        elevation = 5.dp,
        shape = RoundedCornerShape(12.dp) // Adjust the corner shape as needed
    ) {
        IconButton(onClick = onClick) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.background(Color.Transparent)
            ) {
                // Replace R.drawable.ic_heart with your actual heart icon resource
                Icon(
                    Icons.Filled.Chat,
                    contentDescription = "Connect",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
        }
    }
}