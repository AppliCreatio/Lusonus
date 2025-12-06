package com.example.lusonus.data.dataclasses

import android.net.Uri
import com.example.lusonus.data.interfaces.Entries
import java.time.LocalDateTime

data class Media(
    override var name: String,
    override val dateAdded: LocalDateTime,
    override var lastPlayed: LocalDateTime?,
    val uri: Uri,
) : Entries
