package com.paymorecase.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

internal fun handleCameraClick(
    context: Context,
    onCameraPermissionGranted: () -> Unit,
    onTheRationaleShowed: () -> Unit,
    permissionRequesterActivityResultLauncher: ActivityResultLauncher<String>,
    activity: Activity?,
) {
    when {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED -> onCameraPermissionGranted()

        activity?.let {
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.CAMERA
            )
        } == true -> onTheRationaleShowed()

        else -> permissionRequesterActivityResultLauncher.launch(Manifest.permission.CAMERA)
    }
}