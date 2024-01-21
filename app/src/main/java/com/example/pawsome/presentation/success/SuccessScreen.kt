package com.example.pawsome.presentation.success

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawsome.R
import com.example.pawsome.presentation.authentication.components.ButtonComponent


@Preview(showBackground = true)
@Composable
fun SuccessScreen(action: String = "Create", buttonTitle: String = "Go Back", buttonAction: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.tick), contentDescription = "green tick")
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "$action Success", fontSize = 28.sp)
        Spacer(modifier = Modifier.height(550.dp))
        Box(modifier = Modifier.width(300.dp)) {
            ButtonComponent(value = buttonTitle, onButtonClicked = { buttonAction()}, isEnabled = true)
        }
    }
}