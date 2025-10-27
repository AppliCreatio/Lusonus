package com.example.lusonus.data.model

import android.net.Uri
import com.example.lusonus.screens.ProfileScreen.classes.MusicEntryTypes


data class MusicEntry(
    val name: String,
    val imageUri: Uri,
    val author: String,
    val type: MusicEntryTypes
)