package com.example.lusonus.ui.utils

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri

fun <T : CharSequence>search(media: List<T>, query: String, context: Context): List<T> {
    return media.filter { context.getFileName(it.toString().toUri()).lowercase().contains(query) }
}
