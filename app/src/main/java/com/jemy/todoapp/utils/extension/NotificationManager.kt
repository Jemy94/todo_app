package com.jemy.todoapp.utils.extension

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.jemy.todoapp.R
import com.jemy.todoapp.ui.MainActivity

fun NotificationManager.sendNotification(
    title: String,
    body: String,
    applicationContext: Context
) {
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        System.currentTimeMillis().toInt(),
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val builder = NotificationCompat.Builder(
        applicationContext, applicationContext.getString(R.string.default_notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_android)
        .setContentTitle(title)
        .setContentText(body)
        .setContentIntent(contentPendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    notify(System.currentTimeMillis().toInt(), builder.build())
}

