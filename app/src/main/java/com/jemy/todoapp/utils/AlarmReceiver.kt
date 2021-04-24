package com.jemy.todoapp.utils

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.jemy.todoapp.utils.extension.sendNotification

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra(Constants.TITLE)
        val body = intent?.getStringExtra(Constants.BODY)
        if (title != null && body !=null) {
            sendNotification(title, body, context!!)
        }
    }

    private fun sendNotification(title: String, body: String, applicationContext: Context) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(title = title, body = body, applicationContext)
    }
}