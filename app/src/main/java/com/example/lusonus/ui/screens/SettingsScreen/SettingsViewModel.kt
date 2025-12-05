package com.example.lusonus.ui.screens.SettingsScreen

import androidx.lifecycle.ViewModel
import com.example.lusonus.data.model.Settings

class SettingsViewModel(var settings: Settings): ViewModel() {
    fun toggleProfile() {
        settings.profileToggle = !settings.profileToggle
        println("Toggled profile.")
    }

    fun changeFileTypeRestriction() {
        println("Toggled file type restriction.")
    }
}