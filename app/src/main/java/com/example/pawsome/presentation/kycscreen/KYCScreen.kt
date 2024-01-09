package com.example.pawsome.presentation.kycscreen

import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
//import com.example.pawsome.viewmodel.CameraViewModel

@Composable
@Preview
fun CameraScreen(
//            viewModel: CameraViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    val contextCamera = LocalLifecycleOwner.current
//    LaunchedEffect(cameraProviderFuture) {
//        viewModel.startCamera(contextCamera, cameraProviderFuture)
//    }
//
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Button(onClick = {
//            viewModel.takePicture(context)
        }) {
            Text("Take Photo")
        }
        Spacer(modifier = Modifier.height(16.dp))
//        viewModel.imageUri?.let { uri ->
//            AsyncImage(
//                model = uri,
//                contentDescription = "Captured image",
//                modifier = Modifier.size(200.dp)
//            )
//        }
    }
}