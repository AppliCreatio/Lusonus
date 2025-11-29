package com.example.lusonus.data.model

import android.net.Uri

data class Folder(override val name: String, val uri: Uri, val media: MutableList<Media>) : Entries
