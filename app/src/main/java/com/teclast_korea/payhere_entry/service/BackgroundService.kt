package com.teclast_korea.payhere_entry.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.teclast_korea.payhere_entry.R


class BackgroundService : Service() {

    override fun onCreate() {
        super.onCreate()
        // This is called once when the service is created
        // Potentially start as a Foreground service so it's not killed
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Do background tasks here...
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        // If it's not a bound service, just return null
        return null
    }

    private fun startForegroundService() {
        // Must show a persistent notification if it's a real foreground service
        val notificationId = 1001
        val channelId = "my_service_channel"

        // 1) Create the notification channel if needed (for Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Payhere Entry App Service",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
        }

        // 2) Build a minimal notification
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("BackgroundService")
            .setContentText("Running in the background...")
            .build()

        // 3) Start foreground
        startForeground(notificationId, notification)
    }
}