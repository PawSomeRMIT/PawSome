package com.example.pawsome.presentation.kycscreen.confirmation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pawsome.R
import com.example.pawsome.viewmodel.CameraViewModel

@Composable
@Preview
fun KYCConfirmElement(
    ifSuccess: Boolean = false,
    onButtonClickOk: () -> Unit = {},
    onButtonClickFail: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (ifSuccess) {
            Image(
                painterResource(R.drawable.thumb_confirm),
                contentDescription = null,
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Your ID Have Been Confirmed", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onButtonClickOk, modifier = Modifier.width(200.dp)) {
                Text("Continue")
            }
        } else {
            Image(
                painterResource(R.drawable.errror),
                contentDescription = null,
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Your ID Have Not Been Confirmed",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onButtonClickFail, modifier = Modifier.width(200.dp)) {
                Text("Go Back")
            }
        }
    }
}

@Composable
fun KYCScreen(navHostController: NavHostController){
    //If success navigate to home , else it will goback to the previous screen
    KYCConfirmElement(true,{
        //TODO: Define the navigation element
                           navHostController.navigate("home")
    },{
        navHostController.navigate("KYCScreen")
    })
}

