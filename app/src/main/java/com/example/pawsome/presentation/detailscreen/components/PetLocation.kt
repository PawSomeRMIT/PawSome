package com.example.pawsome.presentation.detailscreen.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Preview
@Composable
fun PetLocationMap(petLocation: LatLng = LatLng(0.0, 0.0)) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(petLocation, 12f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), // Adjust the height as needed
        cameraPositionState = cameraPositionState,
        properties = MapProperties(mapType = MapType.NORMAL)

    ) {
        Marker(
            state = MarkerState(position = petLocation),
            title = "Pet Location",
            snippet = "Here is the pet!"
        )
    }
}