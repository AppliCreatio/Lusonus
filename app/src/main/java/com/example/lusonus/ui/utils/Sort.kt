package com.example.lusonus.ui.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

fun sortPlaylists(playlists: SnapshotStateMap<String, SnapshotStateList<String>>, sortType: String): Map<String, SnapshotStateList<String>>{
    return when(sortType) {
            "alphabetically" -> playlists.toSortedMap()
            else -> playlists
    }
}

fun sortMedia(mediaList: List<String>, context: Context, sortType: String? = null): List<String> {

    return mediaList.sortedBy { media -> context.getFileName(media.toUri()) }
}