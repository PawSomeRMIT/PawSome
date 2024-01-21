/*
    RMIT University Vietnam
    Course: COSC2657 Android Development
    Semester: 2023C
    Assessment: Assignment 3
    Author:
        Thieu Tran Tri Thuc - s3870730
        Lai Nghiep Tri - s3799602
        Bui Minh Nhat - s3878174
        Phan Bao Kim Ngan - s3914582
    Created  date: 1/1/2024
    Last modified: 19/1/2024
    Acknowledgement: Figma UI, Android Developer documentation, Firebase Documentation, etc
 */

package com.example.pawsome.presentation.detailscreen.components

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pawsome.R
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun PetLocationMap(
    petLocation: LatLng,
    petLocationMapViewModel: PetLocationMapViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var currentLocation by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }

    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(petLocation, 12f)
    }

    var cameraPositionState by remember {
        mutableStateOf(cameraPosition)
    }

    petLocationMapViewModel.fusedLocationClient = LocationServices.getFusedLocationProviderClient(
        context
    )

    petLocationMapViewModel.locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)

            for (location in p0.locations) {
                currentLocation = LatLng(location.latitude, location.longitude)

                cameraPositionState = CameraPositionState (
                    position = CameraPosition.fromLatLngZoom(currentLocation, 13f)
                )
            }

            petLocationMapViewModel.locationCallback.let {
                petLocationMapViewModel.fusedLocationClient.removeLocationUpdates(it)
            }
        }
    }

    val launchMultiplePermissions = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {permissionMap ->
        val areGranted = permissionMap.values.reduce{ acc, next -> acc && next}

        if (areGranted) {
            petLocationMapViewModel.locationRequired = true

            petLocationMapViewModel.startLocationUpdate()
            Toast.makeText(context, "Permissions Granted", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    val uiSetting by remember {
        mutableStateOf(MapUiSettings())
    }
    val properties by remember {
        mutableStateOf(MapProperties(
            mapType = MapType.NORMAL,
            isMyLocationEnabled = true
        ))
    }

    Box (
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = properties,
            uiSettings = uiSetting,
            cameraPositionState = cameraPositionState,
            onMyLocationButtonClick = {
                if (petLocationMapViewModel.permissions.all {
                        ContextCompat.checkSelfPermission(
                            context,
                            it
                        ) == PackageManager.PERMISSION_GRANTED
                    }) {
                    // Get location
                    petLocationMapViewModel.startLocationUpdate()

                    return@GoogleMap true

                } else {
                    launchMultiplePermissions.launch(petLocationMapViewModel.permissions)

                    return@GoogleMap false
                }
            }

        ) {
            Marker(
                state = MarkerState(position = petLocation),
                title = "Pet Location",
                snippet = "Here is the pet!",
                icon = bitmapDescriptorFromVector(context, R.drawable.petmarker),
            )
        }
    }
}

fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    // Retrieve the actual drawable
    val drawableRes = ContextCompat.getDrawable(context, vectorResId) ?: return null

    drawableRes.setBounds(0, 0, drawableRes.intrinsicWidth, drawableRes.intrinsicHeight)

    val bm = Bitmap.createBitmap(
        drawableRes.intrinsicWidth,
        drawableRes.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bm)
    drawableRes.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bm)
}