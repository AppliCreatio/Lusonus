package com.example.lusonus.ui.screens.PlaylistScreen

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lusonus.data.model.Media
import com.example.lusonus.data.model.Settings
import com.example.lusonus.data.model.SharedMediaLibrary
import com.example.lusonus.data.model.SharedPlaylistLibrary
import com.example.lusonus.ui.utils.filter
import com.example.lusonus.ui.utils.search
import com.example.lusonus.ui.utils.sort
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Media view model to deal with
@RequiresApi(Build.VERSION_CODES.O)
class PlaylistViewModel(private val playlistName: String, val settings: Settings) : ViewModel() {
    // Gets shared singleton instance.
    private val playlistLibrary = SharedPlaylistLibrary
    private val mediaLibrary = SharedMediaLibrary

    // Hot flow holding just this folder's files.
    private val _playlistFiles = MutableStateFlow<List<Media>>(emptyList())

    // READONLY version for UI.
    val playlistFiles: StateFlow<List<Media>> = _playlistFiles.asStateFlow()

    // This is called when view model is made, is helpful since we need to get the playlist safely.
    init {
        viewModelScope.launch {
            playlistLibrary.playlists.collect { playlists ->
                val playlist = playlists.find { it.name == playlistName }
                _playlistFiles.value = playlist?.media ?: emptyList()
            }
        }
    }



    val allMediaFiles: StateFlow<List<Media>> = mediaLibrary.media

    // Refreshes the media list (cleans up dead links).
    fun refreshMedia(context: Context) {
        val base = playlistLibrary.getPlaylist(playlistName)?.media ?: return

        val refreshed = base.filter { media ->
            DocumentFile.fromSingleUri(context, media.uri)?.exists() == true
        }

        _playlistFiles.value = refreshed
    }


    // Adds selected media to this playlist
    fun addToPlaylist(mediaList: List<Media>) {
        playlistLibrary.addMediaToPlaylist(playlistName, mediaList)
    }

    // Removes a media item from this playlist
    fun removeFromPlaylist(media: Media) {
        playlistLibrary.removeMediaFromPlaylist(playlistName, media.uri)
    }

    fun searchMedia(query: String) {
        val base = playlistLibrary.getPlaylist(playlistName)?.media ?: return
        _playlistFiles.value =
            if (query.isBlank()) base
            else search(base, query)
    }

    fun sortMedia(type: String) {
        val base = playlistLibrary.getPlaylist(playlistName)?.media ?: return
        _playlistFiles.value = sort(base, type)
    }

    fun filterByFileType(restrictionType: Int) {
        val base = playlistLibrary.getPlaylist(playlistName)?.media ?: return

        _playlistFiles.value = filter(base, restrictionType)
    }
}