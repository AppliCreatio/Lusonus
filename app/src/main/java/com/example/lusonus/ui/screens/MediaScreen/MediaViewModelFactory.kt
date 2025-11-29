package com.example.lusonus.ui.screens.MediaScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class MediaViewModelFactory(
    private val mediaName: String
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // This bullsh*t is to make sure T is valid, if we don't do this we get an annoying warning.
        // This is mixed with the stuff from the class notes.
        if (modelClass.isAssignableFrom(MediaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MediaViewModel(mediaName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
