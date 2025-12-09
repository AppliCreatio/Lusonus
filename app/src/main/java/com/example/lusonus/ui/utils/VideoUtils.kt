package com.example.lusonus.ui.utils

import android.content.Context
import android.net.Uri

/*
*   Coded by Brandon
*  */

fun Uri.isVideo(context: Context): Boolean {
    val type = context.contentResolver.getType(this)
    return type?.startsWith("video") == true
}
