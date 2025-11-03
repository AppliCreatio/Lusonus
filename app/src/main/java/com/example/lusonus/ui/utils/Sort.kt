package com.example.lusonus.ui.utils

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.core.net.toUri
import com.example.lusonus.data.model.Entries

//fun <T : Entries> sortPlaylists(list: SnapshotStateMap<String, SnapshotStateList<String>>, sortType: String): Map<String, SnapshotStateList<String>>{
//    return when(sortType) {
//            "alphabetically" -> list.toSortedMap()
//            else -> list
//    }
//}

fun <T : Entries> sort(mediaList: List<T>, sortType: String): List<T> {

    return when(sortType){
        "alphabetically" -> mediaList.sortedBy { it.name }
        else -> mediaList
    }
}