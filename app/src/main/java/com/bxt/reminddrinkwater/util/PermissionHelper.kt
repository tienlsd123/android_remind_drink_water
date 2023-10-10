package com.bxt.reminddrinkwater.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat

class StoragePermissionHelper<T : ActivityResultCaller>(
    resultCaller: T,
    private val onGranted: () -> Unit = {},
    private val onDenied: () -> Unit = {},
) {
    companion object {
        private val STORAGE_PERMISSIONS =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            } else {
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }

        fun isAllPermissionGranted(context: Context) =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
            } else true

        fun isAllPermissionGranted(result: Map<String, Boolean>) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            result[Manifest.permission.POST_NOTIFICATIONS] == true
        } else true
    }

    private val permissionRequest = resultCaller.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        actionResult(result)
    }

    private fun actionResult(result: Map<String, Boolean>) {
        if (isAllPermissionGranted(result)) onGranted() else onDenied()
    }

    fun checkStatePermission(context: Context) {
        if (isAllPermissionGranted(context)) onGranted() else requestPermission()
    }

    private fun requestPermission() {
        permissionRequest.launch(STORAGE_PERMISSIONS)
    }
}
