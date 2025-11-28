package com.example.lusonus.data.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class Playlist(override val name: String, val media: SnapshotStateList<Media> = mutableStateListOf()) : Entries