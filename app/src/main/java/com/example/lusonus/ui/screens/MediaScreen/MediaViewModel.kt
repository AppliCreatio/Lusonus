package com.example.lusonus.ui.screens.MediaScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lusonus.data.model.SharedMediaLibrary
import com.example.lusonus.data.model.SharedPlaylistLibrary

// View model for MediaScreen.
class MediaViewModel(private val mediaName: String) : ViewModel() {
    // Gets shared singleton instance.
    private val mediaLibrary = SharedMediaLibrary

    // Gets the media from the name. Does the get(), read next comment for details.
    // This can't be null, but it won't be based on how you get to the screen in the first place.
    val media get() = mediaLibrary.getMedia(mediaName)

    // Property defining whether the Queue is open.
    var isQueueOpen by mutableStateOf(false)
        private set

    // Property defining whether the media is liked.
    // TODO: Add isLiked to media dataclass.
    var isLiked by mutableStateOf(false)
        private set

    // Property defining whether the media is paused.
    var isPaused by mutableStateOf(false)
        private set

    // Method that toggles queue property.
    fun toggleQueue() { isQueueOpen = !isQueueOpen }

    // Method that toggles liked property.
    fun toggleLike() { isLiked = !isLiked }

    // Method that toggles paused property.
    fun togglePause() { isPaused = !isPaused }
}