package com.example.lusonus.data.model

import androidx.compose.runtime.snapshots.SnapshotStateList
import java.util.Date

data class Playlist(override val name: String, val media: SnapshotStateList<Media>) : Entries
//, val dateAdded: Date, val lastPlayed: Date? = null)
