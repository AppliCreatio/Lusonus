package com.example.lusonus.data.sharedinstances

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.lusonus.data.dataclasses.Settings

object Global {
    var mediaPopUpName by mutableStateOf("")
        private set

    fun setMediaPopUpNameToField(name: String) {
        mediaPopUpName = name
    }

    fun clearMediaPopUpName() {
        mediaPopUpName = ""
    }

    var settings by mutableStateOf(Settings(false, 0))
}
