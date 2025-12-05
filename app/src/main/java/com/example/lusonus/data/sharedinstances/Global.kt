package com.example.lusonus.data.sharedinstances

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object Global {
    var mediaPopUpName by mutableStateOf("")
        private set

    fun setMediaPopUpNameToField(name: String) {
        mediaPopUpName = name
    }

    fun clearMediaPopUpName() {
        mediaPopUpName = ""
    }
}