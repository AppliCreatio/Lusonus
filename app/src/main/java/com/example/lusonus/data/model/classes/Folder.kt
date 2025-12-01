package com.example.lusonus.data.model.classes

import android.net.Uri
import com.example.lusonus.data.model.interfaces.Entries
import java.time.LocalDateTime

data class Folder(override val name: String,
                  override val dateAdded: LocalDateTime,
                  override var lastPlayed: LocalDateTime?, val uri: Uri, val media: MutableList<Media>) :
    Entries
