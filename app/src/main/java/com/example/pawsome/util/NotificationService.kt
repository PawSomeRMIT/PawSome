package com.example.pawsome.util

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.pawsome.R

class NotificationService (
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(title: String, content: String) {
        val notification = NotificationCompat.Builder(context, SITE_CHANNEL_ID)
            .setSmallIcon(R.drawable.background_11)
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content))
            .build()

        notificationManager.notify(
            0,
            notification
        )
    }

    companion object {
        const val  SITE_CHANNEL_ID = "PawSome Notification"
    }
}