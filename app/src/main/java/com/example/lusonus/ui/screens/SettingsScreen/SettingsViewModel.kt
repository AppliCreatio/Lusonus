package com.example.lusonus.ui.screens.SettingsScreen

import androidx.lifecycle.ViewModel
import com.example.lusonus.data.model.Settings

class SettingsViewModel(var settings: Settings): ViewModel() {
    fun toggleProfile() {
        settings.profileToggle = !settings.profileToggle
        println("Disable profile: ${settings.profileToggle}")
    }

    fun changeFileTypeRestriction(restrictionType: Int) {
        settings.fileTypeRestriction = restrictionType
    }
}