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

package com.example.pawsome

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.pawsome.domain.NavigationGraph
import com.example.pawsome.util.NotificationService
import com.google.firebase.FirebaseApp
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()

        setContent {
            FirebaseApp.initializeApp(this)

            PaymentConfiguration.init(
                this,
                "pk_test_51OVWbwHJQIp0NYHECOK7IBcTKBKHzWMRgzS22LZjRDjLFUakjumpqrS4eYN7ye9XJTyiy9VjfoJzahKdWdgWkaKo00iyNxfvxq"
            )

            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NavigationGraph()
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NotificationService.SITE_CHANNEL_ID,
            "PawSome Notification",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}
