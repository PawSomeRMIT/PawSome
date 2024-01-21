package com.example.pawsome.presentation.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawsome.R
import com.example.pawsome.common.TitleText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SquareCard(
    name: String,
    icon: ImageVector,
    color: Int = R.color.white,
    textColor: Int = R.color.black,
    iconColor: Int = R.color.black,
    onEventClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(6.dp)
            .size(165.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = color)
        ),
        shape = RoundedCornerShape(20.dp),
        onClick = onEventClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Icon description",
                modifier = Modifier.size(100.dp),
                tint = colorResource(id = iconColor)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TitleText(value = name, fontSize = 20.sp, color = textColor)
        }
    }
}

@Preview
@Composable
fun PreviewSquareCard() {
    SquareCard(name = "My pets", icon = Icons.Outlined.ManageAccounts)
}