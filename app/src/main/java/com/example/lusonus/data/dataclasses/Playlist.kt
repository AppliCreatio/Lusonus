package com.example.lusonus.data.dataclasses

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.lusonus.data.interfaces.Entries
import java.time.LocalDateTime

data class Playlist(
    override val name: String,
    override val dateAdded: LocalDateTime,
    override var lastPlayed: LocalDateTime?,
    val media: SnapshotStateList<Media> = mutableStateListOf()
) :
    Entries