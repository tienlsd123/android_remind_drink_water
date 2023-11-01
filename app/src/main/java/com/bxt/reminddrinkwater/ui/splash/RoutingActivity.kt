package com.bxt.reminddrinkwater.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bxt.reminddrinkwater.R
import com.bxt.reminddrinkwater.ui.main.MainActivity
import com.bxt.reminddrinkwater.util.StoragePermissionHelper
import com.bxt.reminddrinkwater.util.getCalendarNextTime
import com.bxt.reminddrinkwater.worker.RemindDrinkWaterWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class RoutingActivity : AppCompatActivity() {

    private val requestPermission = StoragePermissionHelper(this, onGranted = {
        startActivity(Intent(this, MainActivity::class.java))
        val delay = calculatorInitialDelay()
        val request = PeriodicWorkRequestBuilder<RemindDrinkWaterWorker>(2L, TimeUnit.HOURS)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .addTag(REMIND_WORKER_TAG)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(REMIND_WORKER_NAME, ExistingPeriodicWorkPolicy.KEEP, request)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }, onDenied = { finish() })

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routing)
        splashScreen.setKeepOnScreenCondition { true }
        requestPermission.checkStatePermission(this)
    }

    private fun calculatorInitialDelay(): Long {
        val now = Calendar.getInstance()
        val plusHours = if (now.get(Calendar.HOUR_OF_DAY) % 2 == 0) 2 else 1
        val target = getCalendarNextTime(plusHours)
        return target.timeInMillis - now.timeInMillis
    }

    companion object {
        const val REMIND_WORKER_TAG = "remind_worker"
        const val REMIND_WORKER_NAME = "remind_worker_name"
    }
}
