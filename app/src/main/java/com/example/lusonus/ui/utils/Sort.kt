package com.example.lusonus.ui.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.lusonus.data.interfaces.Entries
import java.time.format.DateTimeFormatter

// fun <T : Entries> sortPlaylists(list: SnapshotStateMap<String, SnapshotStateList<String>>, sortType: String): Map<String, SnapshotStateList<String>>{
//    return when(sortType) {
//            "alphabetically" -> list.toSortedMap()
//            else -> list
//    }
// }

@RequiresApi(Build.VERSION_CODES.O)
fun <T : Entries> sort(
    mediaList: List<T>,
    sortType: String,
): List<T> {
    DateTimeFormatter.ofPattern("dd-MM-yyyy")

    return when (sortType) {
        "alphabetically" -> mediaList.sortedBy { it.name }
        "date added" -> mediaList.sortedBy { it.dateAdded }
        "last played" -> mediaList.sortedByDescending { it.lastPlayed }
        else -> mediaList
    }
}
