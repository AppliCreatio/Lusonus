package com.example.lusonus.ui.screens.FolderViewScreen

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel

class FolderViewViewModel: ViewModel() {
    // this is to import the folder contents, while making sure we only get the videos and audios.
    fun importFolder(context: Context, folderUri: Uri) {
        val folder = DocumentFile.fromTreeUri(context, folderUri)
        val mediaFiles = folder?.listFiles()?.filter { file ->
            file.isFile && (
                    file.type?.startsWith("audio/") == true ||
                            file.type?.startsWith("video/") == true
                    )
        } ?: emptyList()

        // Now you have a list of allowed media files
        //addFiles(mediaFiles.mapNotNull { it.uri })
    }

}