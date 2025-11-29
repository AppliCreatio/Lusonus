package com.example.lusonus.data.model

import android.net.Uri
import java.time.LocalDate
import java.time.LocalDateTime

data class Folder(override val name: String,
                  override val dateAdded: LocalDateTime,
                  override var lastPlayed: LocalDateTime?, val uri: Uri, val media: MutableList<Media>) : Entries
