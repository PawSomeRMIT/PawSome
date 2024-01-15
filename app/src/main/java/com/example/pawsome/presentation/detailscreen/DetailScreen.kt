package com.example.pawsome.presentation.detailscreen


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pawsome.R
import com.example.pawsome.domain.Graph
import com.example.pawsome.domain.PetsListScreen
import com.example.pawsome.model.Booking
import com.example.pawsome.model.PetDetail
import com.example.pawsome.model.User
import com.example.pawsome.presentation.detailscreen.components.DetailChip
import com.example.pawsome.presentation.detailscreen.components.PetLocationMap
import com.example.pawsome.presentation.detailscreen.components.PriceAdoptButton
import com.example.pawsome.presentation.detailscreen.components.VideoCallButton
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun DetailScreen(
    petDetail: PetDetail,
    owner: User,
    onVideoCall: () -> Unit,
    onBackClick: () -> Unit,
    navHostController: NavHostController,
    detailScreenViewModel: DetailScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val auth = FirebaseAuth.getInstance()
    val userID = auth.currentUser?.uid
    var userData by remember(userID) { mutableStateOf(User()) }

    LaunchedEffect(userID) {
        if (userID != null) {
            val userRef = Firebase.firestore.collection("user").document(userID)
            val snapshot = userRef.get().await()

            snapshot?.let {
                val result = User(
                    userID = it.get("userID").toString(),
                    username = it.get("username").toString(),
                    email = it.get("email").toString(),
                    image = it.get("image").toString(),
                    membership = it.get("membership").toString(),
                    chatToken = it.get("chatToken").toString(),
                    history = it.get("history") as List<Booking>
                )

                userData = result
            }
        }
    }

    // Check data loading
    val loadingState by detailScreenViewModel.isLoading.collectAsState(initial = false)

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

    if (loadingState) {
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = colorResource(id = R.color.yellow)
            )
        }
    }
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Box (
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = petDetail.img,
                    contentDescription = "Pet Image",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 25.dp,
                                topEnd = 25.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                )

                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "Back", tint = Color.Black)
                }

            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                shape = RoundedCornerShape(25.dp),
                elevation = 5.dp
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            // Name
                            Text(
                                petDetail.petName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp
                            )

                            // Bredd + Animal
                            Text(
                                petDetail.petBreed + " " + petDetail.petAnimal,
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row (
//                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Icon(
                                    Icons.Default.LocationOn,
                                    contentDescription = "Location",
                                    modifier = Modifier.height(20.dp)
                                )

                                Text(
                                    petDetail.distance.toString() + " km"
                                )
                            }
                        }

                        VideoCallButton(
                            onClick = {
                                scope.launch {
                                    val channelID = detailScreenViewModel.createChannel(owner.email.split("@")[0], userData.email.split("@")[0])

                                    Log.d("MOVE", channelID)

                                    navHostController.currentBackStackEntry?.savedStateHandle?.set("channelId", channelID)

                                    navHostController.navigate(Graph.CHAT)
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            Icons.Outlined.Pets,
                            contentDescription = "Pet",
                            modifier = Modifier.height(35.dp)
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "About ${petDetail.petName}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        item {
                            DetailChip(
                                title = "Gender",
                                info = petDetail.petGender
                            )
                        }
                        item {
                            DetailChip(
                                title = "Breed",
                                info = petDetail.petBreed
                            )
                        }
                        item {
                            DetailChip(
                                title = "Color",
                                info = petDetail.petColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        UserProfile(user = owner)

                        Spacer(modifier = Modifier.height(15.dp))

                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                Icons.Filled.EventAvailable,
                                contentDescription = "Pet",
                                modifier = Modifier.height(30.dp),
                                tint = Color.Green
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(
                                text = "Avaiable",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(15.dp))

                        // Description
                        Text(
                            text = petDetail.petDescription,
                            color = colorResource(id = R.color.text_gray),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Map Location
                        val petLocation = LatLng(petDetail.latitude, petDetail.longitude)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height((LocalConfiguration.current.screenHeightDp * 0.5).dp)
                                .clip(RoundedCornerShape(20.dp)),
                        ) {
                            PetLocationMap(petLocation = petLocation)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        PriceAdoptButton(
                            price = petDetail.bookingPricePerDay.toDouble()
                        ) {
                            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                "petDetail",
                                petDetail
                            )

                            navHostController.navigate(PetsListScreen.EKYCCheckingScreen.route)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserProfile(user: User) {
    // Profile information using AsyncImage
    Row(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = Uri.parse(user.image), // Replace with URL or local drawable resource
            contentDescription = "Owner",
            modifier = Modifier
                .size(75.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(20.dp))

        Column {
            Text(
                user.username,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "Owner",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
