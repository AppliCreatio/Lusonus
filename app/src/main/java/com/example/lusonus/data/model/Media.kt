package com.example.lusonus.data.model

import android.net.Uri
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import java.util.Date

data class Media(override var name: String, val uri: Uri) : Entries
