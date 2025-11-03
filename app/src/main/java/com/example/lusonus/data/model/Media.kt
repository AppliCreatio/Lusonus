package com.example.lusonus.data.model

import android.net.Uri
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import java.util.Date

data class Media(override val name: String, val uri: Uri) : Entries
//, val dateAdded: Long, val lastPlayed: Long? = null)
