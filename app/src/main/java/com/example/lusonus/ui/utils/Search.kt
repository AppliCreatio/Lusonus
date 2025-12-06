package com.example.lusonus.ui.utils

import com.example.lusonus.data.interfaces.Entries

fun <T : Entries> search(
    media: List<T>,
    query: String,
): List<T> = media.filter { it.name.lowercase().contains(query) }
