package com.example.lusonus.data.model

import android.net.Uri

data class Media(override var name: String, val uri: Uri) : Entries
