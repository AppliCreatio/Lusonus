package com.example.lusonus.data.model.classes

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import com.example.lusonus.appContext
import com.example.lusonus.data.dataclasses.Folder
import com.example.lusonus.data.dataclasses.Media
import com.example.lusonus.data.dataclasses.proto.FolderProto
import com.example.lusonus.data.dataclasses.protodatastore.folderLibraryDataStore
import com.example.lusonus.data.sharedinstances.SharedMediaLibrary
import com.example.lusonus.ui.utils.search
import com.example.lusonus.ui.utils.sort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/*
*   Brandon made this entire file
*  */

open class FolderLibrary {
    // Application context.
    private val context: Context = appContext

    // Hot flow that deals with folder changing.
    private val _allFolders = MutableStateFlow<List<Folder>>(emptyList())

    // Currently displayed version.
    private val _folders = MutableStateFlow<List<Folder>>(emptyList())

    // A stable more restricted one for UI use. READONLY
    val folders: StateFlow<List<Folder>> = _folders.asStateFlow()

    // Background scope for IO operations.
    private val scope = CoroutineScope(Dispatchers.IO)

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    // Prevent accidental overwrites before initial load finishes.
    private val _hasRestoredData = MutableStateFlow(false)

    init {
        restoreFromDataStore()
    }

    private fun restoreFromDataStore() {
        scope.launch {
            val stored = context.folderLibraryDataStore.data.first()
            val restored = stored.foldersList.map { it.toFolder() }

            _allFolders.value = restored
            _folders.value = restored

            _hasRestoredData.value = true
        }
    }

    // Convert folder dataclass to the generated proto type.
    private fun Folder.toProto(): FolderProto =
        FolderProto.newBuilder()
            .setName(name)
            .setUri(uri.toString())
            .setDateAdded(dateAdded.format(formatter))
            .setLastPlayed(lastPlayed?.format(formatter) ?: "")
            .addAllMediaItems(media.map { it.toProto() })
            .build()

    // Convert proto to folder dataclass.
    private fun FolderProto.toFolder(): Folder =
        Folder(
            name = name,
            uri = Uri.parse(uri),
            dateAdded = runCatching {
                LocalDateTime.parse(
                    dateAdded,
                    formatter
                )
            }.getOrElse { LocalDateTime.now() },
            lastPlayed = runCatching {
                if (lastPlayed.isBlank()) null else LocalDateTime.parse(
                    lastPlayed,
                    formatter
                )
            }.getOrNull(),
            media = mutableStateListOf<Media>().apply { addAll(mediaItemsList.map { it.toMedia() }) }
        )

    // Convert Media dataclass to proto.
    private fun Media.toProto() =
        com.example.lusonus.data.dataclasses.proto.MediaProto.newBuilder()
            .setUri(uri.toString())
            .setName(name)
            .setDateAdded(dateAdded.format(formatter))
            .setLastPlayed(lastPlayed?.format(formatter) ?: "")
            .build()

    private fun com.example.lusonus.data.dataclasses.proto.MediaProto.toMedia() =
        Media(
            name = name,
            dateAdded = runCatching {
                LocalDateTime.parse(
                    dateAdded,
                    formatter
                )
            }.getOrElse { LocalDateTime.now() },
            lastPlayed = runCatching {
                if (lastPlayed.isBlank()) null else LocalDateTime.parse(
                    lastPlayed,
                    formatter
                )
            }.getOrNull(),
            uri = Uri.parse(uri)
        )

    fun persist() {
        scope.launch {
            if (!_hasRestoredData.value) return@launch

            val current = context.folderLibraryDataStore.data.first()
            val newProtos = _allFolders.value.map { it.toProto() }

            if (newProtos == current.foldersList) return@launch

            context.folderLibraryDataStore.updateData {
                it.toBuilder().clearFolders().addAllFolders(newProtos).build()
            }
        }
    }

    // Adds a folder if it doesn't already exist.
    fun addFolder(name: String, uri: Uri, mediaInFolder: List<Media>) {
        val current = _allFolders.value
        if (current.any { it.uri == uri }) return

        val newFolder = Folder(
            name = name,
            dateAdded = LocalDateTime.now(),
            lastPlayed = null,
            uri = uri,
            media = mediaInFolder.toMutableList()
        )

        _allFolders.value = current + newFolder
        _folders.value = current + newFolder
        persist()
    }

    // Updates the folder.
    fun replaceFolder(newFolder: Folder) {
        _allFolders.value = _allFolders.value.map { if (it.uri == newFolder.uri) newFolder else it }
        _folders.value = _allFolders.value
        persist()
    }

    // Removes the folder by URI.
    fun removeFolder(uri: Uri) {
        val folder = _allFolders.value.find { it.uri == uri } ?: return

        folder.media.forEach { media -> SharedMediaLibrary.removeMedia(media.uri) }

        _allFolders.value = _allFolders.value.filter { it.uri != uri }
        _folders.value = _folders.value.filter { it.uri != uri }
        persist()
    }

    // Gets a Folder by its name.
    fun getFolder(name: String): Folder? = _allFolders.value.find { it.name == name }

    // Sorts folders by a sorting type.
    fun sortFolders(type: String) {
        _folders.value = sort(_folders.value, type)
    }

    // Searches for folders based on a query.
    fun searchFolders(query: String) {
        _folders.value =
            if (query.isBlank()) _allFolders.value else search(_allFolders.value, query)
    }
}
