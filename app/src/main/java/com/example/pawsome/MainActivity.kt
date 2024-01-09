package com.example.pawsome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pawsome.domain.NavigationGraph
import com.example.pawsome.ui.theme.PawSomeTheme
import com.google.firebase.FirebaseApp
import com.stripe.android.BuildConfig
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
    }
}
