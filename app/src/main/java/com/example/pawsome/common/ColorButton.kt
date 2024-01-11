package com.example.pawsome.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.pawsome.R

@Composable
fun ColorButton(
    text: String,
    icon: ImageVector,
    color : Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    isEnable: Boolean = true
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
        enabled = isEnable
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (isEnable) color else colorResource(id = R.color.light_gray),
                    shape = RoundedCornerShape(20.dp)
                )
                .then(modifier),
            contentAlignment = Alignment.Center,
        ) {
            Row (
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    color = Color.White,
                )

                Icon(imageVector = icon, contentDescription = "Icon description")
            }
        }
    }
}