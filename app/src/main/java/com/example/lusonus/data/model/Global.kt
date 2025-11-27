package com.example.lusonus.data.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue

object Global {
    var mediaPopUpName by mutableStateOf("")
        private set

    fun setMediaPopUpName(name: String) {
        mediaPopUpName = name
    }

    fun clearMediaPopUpName() {
        mediaPopUpName = ""
    }
}