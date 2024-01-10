package drawable

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pawsome.R
import com.example.pawsome.viewmodel.CameraViewModel

//import com.example.pawsome.viewmodel.CameraViewModel

@Composable
fun KYCUploadScreen(
    viewModel: CameraViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    // Launch gallery intent
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        // Handle the selected image URI
        uri?.let {
            // viewModel.setImageUri(it)
        }
    }

    val contextCamera = LocalLifecycleOwner.current
    LaunchedEffect(cameraProviderFuture) {
        viewModel.startCamera(contextCamera, cameraProviderFuture)
    }

    KYCContent(
        takePhotoAction = { viewModel.takePicture(context) },
        selectFromGalleryAction = { galleryLauncher.launch("image/*") },
        imageUri = viewModel.imageUri
    )
}

@Composable
fun KYCContent(
    takePhotoAction: () -> Unit,
    selectFromGalleryAction: () -> Unit,
    imageUri: Uri?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (imageUri != null) {
            // Display the selected or captured image
            AsyncImage(
                model = imageUri,
                contentDescription = "Captured or selected image",
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
            )
        } else {
            // Display the placeholder image
            Image(
                painterResource(R.drawable.passport_id_card_travel),
                contentDescription = null,
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Upload Your Passport/ID",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(20.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Buttons for taking photo and selecting from gallery
        Button(onClick = takePhotoAction, modifier = Modifier.width(200.dp)) {
            Text("Take Photo")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = selectFromGalleryAction, modifier = Modifier.width(200.dp)) {
            Text("Select from Gallery")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun KYCContentPreview() {
    KYCContent(
        takePhotoAction = {},
        selectFromGalleryAction = {},
        imageUri = null // Replace with a sample Uri for a better preview
    )
}