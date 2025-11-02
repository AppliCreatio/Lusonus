package com.example.lusonus.ui.utils

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap

fun sortPlaylists(playlists: SnapshotStateMap<String, SnapshotStateList<String>>, sortType: String): SnapshotStateMap<String, SnapshotStateList<String>>{
    return mutableStateMapOf<String, SnapshotStateList<String>>().apply{ putAll(
        when(sortType) {
            "alphabetically" -> playlists.toSortedMap()
            else -> playlists
        }
    )
    }
}