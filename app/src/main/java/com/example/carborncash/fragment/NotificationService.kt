package com.example.carborncash.fragment

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat

class NotificationService : Service() {

    private val notificationId = 45
    private lateinit var notificationManager: NotificationManager
    private val CHANNEL_ID = "your_channel_id"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel(CHANNEL_ID, "Your Channel Name", "Channel Description")
        displayNotification()
        return START_STICKY
    }

    private fun displayNotification() {
        val color = ContextCompat.getColor(this, com.example.carborncash.R.color.logogreen)

        val mlargeicon = when {
            (DataProvider.changemb(DataProvider.getDayUsage(this)).toInt() * 11) < 10000 -> BitmapFactory.decodeResource(resources, com.example.carborncash.R.drawable.fivetree)
            (DataProvider.changemb(DataProvider.getDayUsage(this)).toInt() * 11) < 20000 -> BitmapFactory.decodeResource(resources, com.example.carborncash.R.drawable.fourtree)
            (DataProvider.changemb(DataProvider.getDayUsage(this)).toInt() * 11) < 30000 -> BitmapFactory.decodeResource(resources, com.example.carborncash.R.drawable.threetree)
            (DataProvider.changemb(DataProvider.getDayUsage(this)).toInt() * 11) < 40000 -> BitmapFactory.decodeResource(resources, com.example.carborncash.R.drawable.twotree)
            else -> BitmapFactory.decodeResource(resources, com.example.carborncash.R.drawable.onetree)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(com.example.carborncash.R.drawable.carbonlogo)
            .setLargeIcon(mlargeicon)
            .setContentTitle(HtmlCompat.fromHtml("<font color=\"$color\">Today Carbon Use</font>", HtmlCompat.FROM_HTML_MODE_LEGACY))
            .setContentText("usage: ${(DataProvider.changemb(DataProvider.getDayUsage(this)).toInt() * 11)}g / 55000g")
            .setOngoing(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    private fun createNotificationChannel(channelId: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = channelDescription
            }
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}