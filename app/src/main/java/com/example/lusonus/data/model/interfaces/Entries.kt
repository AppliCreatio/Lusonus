package com.example.lusonus.data.model.interfaces

import java.time.LocalDateTime

interface Entries {
    val name: String
    val dateAdded: LocalDateTime
    val lastPlayed: LocalDateTime?
}