package com.example.lusonus.ui.screens.PlaylistScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlaylistViewModelFactory(
    private val playlistName: String,
) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // This bullsh*t is to make sure T is valid, if we don't do this we get an annoying warning.
        // This is mixed with the stuff from the class notes.
        if (modelClass.isAssignableFrom(PlaylistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaylistViewModel(playlistName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
