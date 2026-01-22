package com.example.latihanfirebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // Dijalankan ketika pesan FCM diterima saat aplikasi aktif (foreground)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Menampilkan pengirim pesan di Logcat
        Log.d("FCM", "From: ${remoteMessage.from}")

        // Jika pesan mengandung payload notifikasi
        remoteMessage.notification?.let {
            Log.d("FCM", "Message Body: ${it.body}")
            showNotification(it.title, it.body)
        }
    }

    // Dijalankan saat token FCM diperbarui
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Refreshed token: $token")
    }

    // Fungsi untuk menampilkan notifikasi secara manual
    private fun showNotification(title: String?, messageBody: String?) {
        val channelId = "MyNotificationChannel"

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Icon notifikasi
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Membuat Notification Channel (Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel Latihan Firebase",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Menampilkan notifikasi
        notificationManager.notify(0, notificationBuilder.build())
    }
}
