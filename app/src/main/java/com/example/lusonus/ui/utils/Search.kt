package com.example.lusonus.ui.utils

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.example.lusonus.data.model.Entries

fun <T : Entries> search(media: List<T>, query: String): List<T> {
    return media.filter { it.name.lowercase().contains(query) }
}
