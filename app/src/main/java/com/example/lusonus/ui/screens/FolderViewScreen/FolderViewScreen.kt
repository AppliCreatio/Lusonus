package com.example.lusonus.ui.screens.FolderViewScreen

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.example.lusonus.ui.composables.Layout.MainLayout

@Composable
fun FolderViewScreen(){
    // This is similar to the media picker, but it's for folders instead.
//    val pickFolderLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri: Uri? ->
//            uri?.let { folderUri ->
//                // Persist permissions so you can read later
//                val contentResolver = context.contentResolver
//                val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
//                contentResolver.takePersistableUriPermission(folderUri, takeFlags)
//
//                // Now you can read files from this folder
//                viewModel.importFolder(folderUri)
//            }
//        }


    MainLayout({

    }, "Folders")
}