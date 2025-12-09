package com.example.lusonus.data.interfaces

import java.time.LocalDateTime

/*
*   Alex made this entire file
*  */

interface Entries {
    val name: String
    val dateAdded: LocalDateTime
    val lastPlayed: LocalDateTime?
}
