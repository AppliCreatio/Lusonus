package com.example.lusonus.data.dataclasses.protodatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.lusonus.data.dataclasses.proto.ProfileProto
import com.example.lusonus.data.dataclasses.protodatastore.serializers.ProfileSerializer

val Context.profileDataStore: DataStore<ProfileProto> by dataStore(
    fileName = "profile.pb",
    serializer = ProfileSerializer
)
