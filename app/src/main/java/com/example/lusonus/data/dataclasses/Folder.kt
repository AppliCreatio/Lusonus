package com.example.lusonus.data.dataclasses

import android.net.Uri
import com.example.lusonus.data.interfaces.Entries
import java.time.LocalDateTime

/*
*   Brandon made this entire file
*  */

data class Folder(
    override val name: String,
    override val dateAdded: LocalDateTime,
    override var lastPlayed: LocalDateTime?,
    val uri: Uri,
    val media: MutableList<Media>,
) : Entries
