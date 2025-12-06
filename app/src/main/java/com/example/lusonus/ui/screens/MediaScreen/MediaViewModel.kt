package com.example.lusonus.ui.screens.MediaScreen

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lusonus.data.sharedinstances.SharedMediaLibrary
import java.time.LocalDateTime

// View model for MediaScreen.
@RequiresApi(Build.VERSION_CODES.O)
class MediaViewModel(
    private var mediaName: String,
) : ViewModel() {
    // Gets shared singleton instance.
    private val mediaLibrary = SharedMediaLibrary

    // Gets the media from the name. Does the get(), read next comment for details.
    // This can't be null, but it won't be based on how you get to the screen in the first place.
    val media get() = mediaLibrary.getMedia(mediaName)

    var hasStarted by mutableStateOf(false)
        private set

    // Property defining whether the Queue is open.
    var isQueueOpen by mutableStateOf(false)
        private set

    // Property defining whether the media is liked.
    // TODO: Add isLiked to media dataclass.
    var isLiked by mutableStateOf(false)
        private set

    fun toggleStartedPlaying() {
        hasStarted = true
    }

    // Method that toggles queue property.
    fun toggleQueue() {
        isQueueOpen = !isQueueOpen
    }

    // Method that toggles liked property.
    fun toggleLike() {
        isLiked = !isLiked
    }

    // Playback related states
    var isPlaying by mutableStateOf(false)
        private set

    var positionMilliseconds by mutableLongStateOf(0L)
        private set

    var durationMilliseconds by mutableLongStateOf(0L)
        private set

    // This would be null if we require a default.
    var artworkBitmap by mutableStateOf<Bitmap?>(null)
        private set

    fun updateMedia(newMediaName: String) {
        hasStarted = false
        mediaName = newMediaName
    }

    // Method that toggles paused property.
    // Note, this is local. The main one comes from broadcast.
    fun togglePause() {
        isPlaying = !isPlaying
    }

    // This is called by BroadcastReceiver in compose when we receive the playback updates.
    fun updatePlaybackState(
        isPlaying: Boolean,
        position: Long,
        duration: Long,
        artworkBytes: ByteArray?,
    ) {
        media!!.lastPlayed = LocalDateTime.now()

        // Sets whether it's playing.
        this.isPlaying = isPlaying

        // Sets the new positon.
        this.positionMilliseconds = position

        // Sets the duration the position is relative to.
        this.durationMilliseconds = duration

        // Tries to set the artwork
        artworkBytes?.let {
            try {
                // Uses the android built in gear to get the bitmap for the artwork.
                artworkBitmap = android.graphics.BitmapFactory.decodeByteArray(it, 0, it.size)
            } catch (e: Exception) {
                // If we fail to get the bitmap I want to see the error.
                e.printStackTrace()
            }
        }
    }

    // This is just a proper setter for the bitmap.
    fun updateArtworkBitmap(bitmap: Bitmap) {
        artworkBitmap = bitmap
    }
}
