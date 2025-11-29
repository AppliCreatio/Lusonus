package com.example.lusonus.ui.utils

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