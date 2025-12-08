package com.example.lusonus.data.dataclasses.protodatastore

import SettingsSerializer
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.lusonus.data.dataclasses.proto.Settings

val Context.settingsDataStore: DataStore<Settings> by dataStore(
    fileName = "settings.pb",
    serializer = SettingsSerializer
)
