package com.example.pawsome.presentation.settings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pawsome.R
import com.example.pawsome.common.NormalText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SquareCard(
    name: String,
    image: Int,
    onEventClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .background(
                color = colorResource(id = R.color.light_gray)
            )
            .padding(6.dp)
            .size(140.dp)
            .fillMaxSize()
            .padding(6.dp),
        shape = RoundedCornerShape(20.dp),
        onClick = onEventClick
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "Card image",
                modifier = Modifier.size(100.dp))
            Spacer(modifier = Modifier.height(8.dp))
            NormalText(value = name)
        }
    }
}

@Preview
@Composable
fun PreviewSquareCard() {
    SquareCard(name = "My pets", image = R.drawable.baseline_pets_24)
}