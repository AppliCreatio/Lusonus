package com.example.lusonus.ui.composables.MediaComposables.MediaPopUp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MediaPopUpViewModel: ViewModel () {
    var mediaId = mutableStateOf(0)
    var mediaName = mutableStateOf("")
    var mediaArtist = mutableStateOf("")
    var currentPlaylistId = mutableStateOf(0)
    var isPaused = false

    // TODO: Change parameters (mediaId, mediaName, mediaArtist) to be instead accessible via media file metadata

    fun play() {}

    fun pause() {}

    fun nextMedia() {}

    fun previousMedia() {}

    fun timeStampChange() {}
}