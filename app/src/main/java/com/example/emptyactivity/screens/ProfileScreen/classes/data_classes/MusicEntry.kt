package com.example.emptyactivity.screens.ProfileScreen.classes.data_classes

import android.net.Uri
import com.example.emptyactivity.screens.ProfileScreen.classes.MusicEntryTypes


data class MusicEntry(
    val name: String,
    val imageUri: Uri,
    val author: String,
    val type: MusicEntryTypes
)