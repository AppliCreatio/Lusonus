package com.example.emptyactivity.screens.FileImportScreen.Screens.Media

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organisemedia.Layout.FloatingActionButton.SharedFloatingActionButton
import com.example.emptyactivity.screens.FileImportScreen.Layout.MainLayout
import com.example.emptyactivity.navigation.LocalNavController
import com.example.organisemedia.ViewModels.MediaViewModel
import com.example.organisemedia.ViewModels.PlaylistViewModel

@Composable
fun MediaScreen() {
    // Gets the view model information
    val viewModel: MediaViewModel = viewModel(viewModelStoreOwner = LocalNavController.current.context as ComponentActivity) // Gets an existing MediaViewModel if it exists.
    val playlistViewModel: PlaylistViewModel = viewModel(viewModelStoreOwner =LocalNavController.current.context as ComponentActivity)

    // Gets files from view model.
    val files = viewModel.files

    // This is a very fancy thing!
    // It uses this thing called Activity Result API,
    // which basically allows you to open the default file picker.
    // I had an issue where you couldn't select multiple files,
    // but then I found OpenMultipleDocuments which does just that.
    // After the user selects files, it adds them to a List<Uri>? (which is "basically" pointers)
    // We add them to selectedFile (is below) so we can display them!
    val pickFilesLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) { uris: List<Uri>? ->
            // Reminder: .let lets you use "it" to refer to the thing "." is appended to...
            // In this case "uris?".
            uris?.let {
                // We map over each uris and convert it to a string.
                // Has to be in strings because you can't use rememberSaveable on uris.
                viewModel.addFiles(it)
            }
        }

    MainLayout(
        content = {
            MediaContent(
                files = files,
                onDeleteMedia = { uriString ->
                    viewModel.removeFile(uriString, playlistViewModel = playlistViewModel)
                }
            )
        },
        screenTitle = "Media",
        floatingActionButton = {
            SharedFloatingActionButton(
                onClick = {
                    // Launch the file picker
                    pickFilesLauncher.launch(input = arrayOf("audio/*", "video/*"))
                }
            )
        }
    )
}