package com.spacewhale.wifitester.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.spacewhale.wifitester.feature.scannerWIFI.REQUEST_ID_PERMISSIONS

val locationCoarsePermission: String
    get() = Manifest.permission.ACCESS_COARSE_LOCATION
val locationFinePermission: String
    get() = Manifest.permission.ACCESS_FINE_LOCATION

fun isLocationPermissionGranted(context: Context) = isLocationCoarsePermissionGranted(context) &&
        isLocationFinePermissionGranted(context)

private fun isLocationCoarsePermissionGranted(context: Context) = ContextCompat.checkSelfPermission(
    context,
    locationCoarsePermission
) != PackageManager.PERMISSION_GRANTED

private fun isLocationFinePermissionGranted(context: Context) = ContextCompat.checkSelfPermission(
    context,
    locationFinePermission
) != PackageManager.PERMISSION_GRANTED

fun isPermissionResultSuccess(requestCode: Int, grantResults: IntArray): Boolean {
    var isGranted = true
    for (grantResult in grantResults) {
        isGranted = isGranted && grantResult == PermissionChecker.PERMISSION_GRANTED
    }
    return requestCode == REQUEST_ID_PERMISSIONS && isGranted
}