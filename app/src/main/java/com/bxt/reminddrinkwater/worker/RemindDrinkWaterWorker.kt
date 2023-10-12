package com.bxt.reminddrinkwater.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bxt.reminddrinkwater.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemindDrinkWaterWorker(context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        val notificationBuild = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_app_foreground)
            .setContentTitle(applicationContext.getString(R.string.app_name))
            .setContentText(CONTENT_TEXT)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notificationBuild)
        Result.success()
    }

    companion object {
        const val CHANNEL_ID = "notification_remind"
        const val CHANNEL_NAME = "notification_remind_n"
        const val CONTENT_TEXT = "It's time to drink a glass of water"
    }
}
