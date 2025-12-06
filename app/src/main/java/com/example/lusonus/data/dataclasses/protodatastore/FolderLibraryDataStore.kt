package com.example.lusonus.data.dataclasses.protodatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.lusonus.data.dataclasses.proto.FolderLibraryProto
import com.example.lusonus.data.dataclasses.protodatastore.serializers.FolderLibrarySerializer

val Context.folderLibraryDataStore: DataStore<FolderLibraryProto> by dataStore(
    fileName = "folder_library.pb",
    serializer = FolderLibrarySerializer,
)