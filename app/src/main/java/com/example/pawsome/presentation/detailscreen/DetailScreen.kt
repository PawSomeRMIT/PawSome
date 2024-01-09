package com.example.pawsome.presentation.detailscreen


import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pawsome.model.PetDetail
import com.example.pawsome.model.generateFakePetDetail
import com.example.pawsome.presentation.detailscreen.components.DetailChip
import com.example.pawsome.presentation.detailscreen.components.PetLocationMap
import com.example.pawsome.presentation.detailscreen.components.PriceAdoptButton
import com.example.pawsome.presentation.detailscreen.components.VideoCallButton

@Composable
fun PetDetailUI(
    petDetail: PetDetail,
    ownerName: String,
    ownerProfileImg: String,
    onAdoptClick: () -> Unit,
    onVideoCall: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, you can access the location
        } else {
            // Permission denied, handle the denial
        }
    }

    val checkAndRequestPermission = {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                // Permissions are granted, proceed with your functionality
            }

            else -> {
                // Permissions are not granted, request them
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(onClick = onBackClick) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(petDetail.img)
                .crossfade(true)
                .build(),
            contentDescription = "Pet Image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(287f / 175f)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(550.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(petDetail.petName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Row {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = "Location",
                                modifier = Modifier.height(20.dp)
                            )
                            Text(petDetail.location)
                        }
                    }
                    VideoCallButton {
                        onVideoCall()
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    item { DetailChip(text = "Gender: ${petDetail.petGender}") }
                    item { DetailChip(text = "Breed: ${petDetail.petBreed}") }
                    item { DetailChip(text = "Color: ${petDetail.petColor}") }
                    item { DetailChip(text = "Animal: ${petDetail.petAnimal}") }
                }

                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                ) {
                    item {
                        UserProfile(ownerProfileImg, ownerName)
                    }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Avaiable: ")
                            Text("Feb 25, 2022")
                        }
                    }
                    item {

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    item {
                        Text(petDetail.petDescription)
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    item {
                        val petLocation =
                            petDetail.latLng// Replace with actual latitude and longitude
                        Box(modifier = Modifier.fillMaxWidth().height(200.dp).padding(start = 4.dp, end = 4.dp)) {
                            PetLocationMap(petLocation)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    PriceAdoptButton(price = petDetail.bookingPricePerDay) {
                        onAdoptClick()
                    }
                }
            }
        }
    }
}

@Composable
fun UserProfile(profileImg: String, profileName: String) {
    // Profile information using AsyncImage
    Row(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = profileImg, // Replace with URL or local drawable resource
            contentDescription = "Owner",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(profileName, fontWeight = FontWeight.Bold)
            Text("Owner")
        }
    }
}

@Preview
@Composable
fun PreviewCatProfileUI() {
    PetDetailUI(generateFakePetDetail(),"","",{},{},{})
}
