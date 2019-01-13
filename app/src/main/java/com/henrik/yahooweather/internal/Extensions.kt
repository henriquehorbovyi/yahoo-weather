package com.henrik.yahooweather.internal

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat

/** Making notification bar transparent */
fun Activity.statusBarColor(color: Int){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = color
        }
    }
}

fun Activity.isPermissionGranted(
    permission: String,
    permissionNotGranted: () -> Unit,
    permissionGranted: () -> Unit) {
    if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
        permissionNotGranted()
    }else{
        permissionGranted()
    }
}