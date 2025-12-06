package com.example.lusonus.ui.screens.SettingsScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lusonus.data.model.Settings

class SettingsViewModelFactory(
    var settings : Settings
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(settings) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
