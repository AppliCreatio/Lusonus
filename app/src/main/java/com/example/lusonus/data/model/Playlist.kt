package com.example.lusonus.data.model

import androidx.compose.runtime.snapshots.SnapshotStateList
import java.util.Date

data class Playlist(val name: String, val media: SnapshotStateList<Media>)
//, val dateAdded: Date, val lastPlayed: Date? = null)
