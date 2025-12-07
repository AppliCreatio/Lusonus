package com.example.lusonus.ui.screens.SettingsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lusonus.appContext
import com.example.lusonus.data.dataclasses.protodatastore.settingsDataStore
import com.example.lusonus.data.sharedinstances.Global
import com.example.lusonus.data.dataclasses.proto.Settings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    var settings by mutableStateOf(Settings.getDefaultInstance())
        private set

    init {
        viewModelScope.launch {
            val proto = appContext.settingsDataStore.data.first()
            settings = proto
            updateGlobalSettings(proto)
        }
    }

    fun toggleProfile() = viewModelScope.launch {
        val updated = settings.toBuilder()
            .setProfileToggle(!settings.profileToggle)
            .build()
        settings = updated

        updateGlobalSettings(updated)
        persist(updated)
    }

    fun changeFileTypeRestriction(type: Int) = viewModelScope.launch {
        val updated = settings.toBuilder()
            .setFileTypeRestriction(type)
            .build()
        settings = updated

        updateGlobalSettings(updated)
        persist(updated)
    }

    private fun updateGlobalSettings(proto: Settings) {
        Global.settings = com.example.lusonus.data.dataclasses.Settings(
            proto.profileToggle,
            proto.fileTypeRestriction,
        )
    }

    private suspend fun persist(value: Settings) {
        appContext.settingsDataStore.updateData { value }
    }
}
