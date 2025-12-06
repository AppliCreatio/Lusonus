package com.example.lusonus.data.dataclasses.protodatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.lusonus.data.dataclasses.proto.PlaylistLibraryProto
import com.example.lusonus.data.dataclasses.protodatastore.serializers.PlaylistLibrarySerializer

val Context.playlistLibraryDataStore: DataStore<PlaylistLibraryProto> by dataStore(
    fileName = "playlist_library.pb",
    serializer = PlaylistLibrarySerializer,
)