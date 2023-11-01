package com.bxt.reminddrinkwater.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bxt.reminddrinkwater.R
import com.bxt.reminddrinkwater.data.AppDatabase
import com.bxt.reminddrinkwater.data.Message
import com.bxt.reminddrinkwater.data.MessageRepository
import com.bxt.reminddrinkwater.util.listMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.random.Random

class RemindDrinkWaterWorker(context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        if (isEvening()) return@withContext Result.success()

        val msg = listMessage[Random.nextInt(0, 9)]
        postNotification(msg)
        saveHistoryMessage(msg)
        Result.success()
    }

    private suspend fun saveHistoryMessage(msg: String) = withContext(Dispatchers.IO) {
        val repository = MessageRepository(AppDatabase.getInstance(applicationContext).messageDao())
        repository.insertMessage(Message(content = msg))
    }

    private fun postNotification(msg: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        val notificationBuild = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_un_background)
            .setContentTitle(applicationContext.getString(R.string.app_name))
            .setContentText(msg)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notificationBuild)
    }

    private fun isEvening(): Boolean {
        val house = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return (1..6).contains(house) || (22..24).contains(house)
    }

    companion object {
        const val CHANNEL_ID = "notification_remind"
        const val CHANNEL_NAME = "notification_remind_n"
    }
}
