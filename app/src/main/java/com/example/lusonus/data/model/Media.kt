package com.example.lusonus.data.model

import android.net.Uri
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import java.util.Date

data class Media(val name: String, val uri: Uri)
//, val dateAdded: Long, val lastPlayed: Long? = null)
