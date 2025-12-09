package com.example.lusonus.data.dataclasses.protodatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.lusonus.data.dataclasses.proto.MediaLibraryProto
import com.example.lusonus.data.dataclasses.protodatastore.serializers.MediaLibrarySerializer

/*
*   Brandon made this entire file
*  */

val Context.mediaLibraryDataStore: DataStore<MediaLibraryProto> by dataStore(
    fileName = "media_library.pb",
    serializer = MediaLibrarySerializer,
)
