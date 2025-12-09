package com.example.lusonus.data.dataclasses

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.lusonus.data.interfaces.Entries
import java.time.LocalDateTime

/*
*   Brandon and Alex made this entire file
*  */

data class Playlist(
    override val name: String,
    override val dateAdded: LocalDateTime,
    override var lastPlayed: LocalDateTime?,
    val image: Uri?,
    val media: SnapshotStateList<Media> = mutableStateListOf(),
) : Entries
