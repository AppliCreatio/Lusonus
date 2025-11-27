package com.example.lusonus.ui.composables.MediaLibraryComposables

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.lusonus.data.model.Media

val MediaListSaver: Saver<SnapshotStateList<Media>, Any> = listSaver(
    save = { list: SnapshotStateList<Media> -> list.map { it.uri.toString() } },
    restore = { savedList: List<String> ->
        val list = mutableStateListOf<Media>()
        savedList.forEach { uriStr ->
            val uri = Uri.parse(uriStr)
            val name = uri.lastPathSegment ?: "Unknown"
            list.add(Media(name, uri))
        }
        list
    }
)