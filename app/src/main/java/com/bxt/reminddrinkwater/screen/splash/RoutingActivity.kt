package com.bxt.reminddrinkwater.screen.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bxt.reminddrinkwater.R
import com.bxt.reminddrinkwater.screen.main.MainActivity
import com.bxt.reminddrinkwater.util.StoragePermissionHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutingActivity : AppCompatActivity() {

    private val requestPermission = StoragePermissionHelper(this, onGranted = {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }, onDenied = {
        finish()
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routing)
        splashScreen.setKeepOnScreenCondition { true }
        requestPermission.checkStatePermission(this)
    }
}
