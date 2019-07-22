package com.example.WhateverMyAge

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionCheck(context : Context, activity: Activity) {

    val context = context
    val activity = activity

    fun CameraCheck() : Int {
        var checked = 0
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.CAMERA), 0)
            checked = 1
        }

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
            checked = 1
        }

        return checked
    }



    fun GalleryCheck() : Int {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 0)
            return 1
        }
        return 0
    }


    fun ContactCheck() : Int{
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_CONTACTS), 0)
            return 1
        }
        return 0
    }


    fun LocationCheck() : Int {
        var checked = 0
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0)
            checked = 1
        }
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 0)
            checked = 1
        }

        return checked
    }
}
