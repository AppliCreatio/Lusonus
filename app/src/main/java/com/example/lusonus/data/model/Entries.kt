package com.example.lusonus.data.model

import java.time.LocalDate
import java.time.LocalDateTime

interface Entries {
    val name: String
    val dateAdded: LocalDateTime
    val lastPlayed: LocalDateTime?
}