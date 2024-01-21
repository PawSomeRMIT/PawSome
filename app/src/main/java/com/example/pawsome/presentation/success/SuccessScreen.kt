package com.example.pawsome.presentation.success

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
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
        Icon(imageVector = Icons.Outlined.Check, contentDescription = "Check icon",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(color = colorResource(id = R.color.green))
                .padding(4.dp),
            tint = colorResource(id = R.color.white))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "$action Success", fontSize = 28.sp)
        Spacer(modifier = Modifier.height(50.dp))
        Box(modifier = Modifier.width(300.dp)) {
            ButtonComponent(value = buttonTitle, onButtonClicked = { buttonAction()}, isEnabled = true)
        }
    }
}
