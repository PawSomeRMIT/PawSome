package com.example.pawsome

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pawsome.domain.NavigationGraph
import com.example.pawsome.ui.theme.PawSomeTheme
import com.google.firebase.FirebaseApp
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseApp.initializeApp(this)

            PaymentConfiguration.init(
                this,
                "pk_test_51OVWbwHJQIp0NYHECOK7IBcTKBKHzWMRgzS22LZjRDjLFUakjumpqrS4eYN7ye9XJTyiy9VjfoJzahKdWdgWkaKo00iyNxfvxq"
            )

            PawSomeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph()
                }
            }
        }
//        requestCameraPermission()
//        requestLocationPermission()

    }
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Continue the action or workflow in your app.
            Log.i("kilo", "Permission granted")
        } else {
            // Explain to the user that the feature is unavailable because the
            // features requires a permission that the user has denied.
            Log.i("kilo", "Permission denied")
        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("kilo", "Permission previously granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.CAMERA
            ) -> Log.i("kilo", "Show camera permissions dialog")

            else -> requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private val requestPermissionLauncherMap = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Get the location.
            getLocation()
        } else {
            // Explain why the permission is needed.
        }
    }

    private fun requestLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission is already granted; get the location.
                getLocation()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                // Show an explanation to the user.
            }

            else -> {
                // No explanation needed; request the permission.
                requestPermissionLauncherMap.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }


}
