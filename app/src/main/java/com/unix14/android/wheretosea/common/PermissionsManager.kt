package com.unix14.android.wheretosea.common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION




class PermissionsManager {
    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 420

        fun getLocationPermission(activity: Activity) : Boolean {
            /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
            return if (ContextCompat.checkSelfPermission(activity,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                ActivityCompat.requestPermissions(activity,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION_REQUEST_CODE)
                canAccessLocation(activity)
            }
        }

        fun canAccessLocation(context: Context): Boolean {
            return hasPermission(context,Manifest.permission.ACCESS_FINE_LOCATION)
        }

        fun canAccessCamera(context: Context): Boolean {
            return hasPermission(context,Manifest.permission.CAMERA)
        }

        fun canAccessContacts(context: Context): Boolean {
            return hasPermission(context,Manifest.permission.READ_CONTACTS)
        }

        private fun hasPermission(context: Context, perm: String): Boolean {
            return PackageManager.PERMISSION_GRANTED == checkSelfPermission(context,perm)
        }


    }
}