package com.example.lusonus.ui.utils

import com.example.lusonus.data.interfaces.Entries

/*
*   Coded by Alex
*  */

// filters out the list of media according to the string passed into it
fun <T : Entries> search(
    media: List<T>,
    query: String,
): List<T> = media.filter { it.name.lowercase().contains(query) }
