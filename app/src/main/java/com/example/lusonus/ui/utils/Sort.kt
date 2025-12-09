package com.example.lusonus.ui.utils

import com.example.lusonus.data.interfaces.Entries
import java.time.format.DateTimeFormatter

/*
*   Coded by Alex
*  */

// Generic method that sorts the media entries according to the string passed into
// Kotlin is kind and has built in sorting methods based on dataclass property
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
