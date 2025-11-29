package com.example.lusonus.data.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.time.LocalDate
import java.time.LocalDateTime

data class Playlist(override val name: String, override val dateAdded: LocalDateTime,
                    override var lastPlayed: LocalDateTime?, val media: SnapshotStateList<Media> = mutableStateListOf()) : Entries