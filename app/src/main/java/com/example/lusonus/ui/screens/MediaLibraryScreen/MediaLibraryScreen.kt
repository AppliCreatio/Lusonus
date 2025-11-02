package com.example.lusonus.ui.screens.MediaLibraryScreen

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.data.model.MenuItem
import com.example.organisemedia.Layout.FloatingActionButton.SharedFloatingActionButton
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.ui.composables.Layout.Buttons.MenuDropDown.MinimalDropdownMenu
import com.example.lusonus.ui.screens.PlaylistLibraryScreen.PlaylistLibraryViewModel

@Composable
fun MediaLibraryScreen() {
    // Gets the view model information
    val viewModel: MediaLibraryViewModel = viewModel(viewModelStoreOwner = LocalNavController.current.context as ComponentActivity) // Gets an existing MediaViewModel if it exists.
    val playlistLibraryViewModel: PlaylistLibraryViewModel = viewModel(viewModelStoreOwner =LocalNavController.current.context as ComponentActivity)

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

    var expanded by remember { mutableStateOf(false) }

    val context = LocalContext.current

    MainLayout(
        content = {

            val sortOptions = listOf<MenuItem>(
                MenuItem("Alphabetical") { viewModel.sortFiles("alphabetically", context) }
            )

            Row() {
                MinimalDropdownMenu(sortOptions, expanded, { expanded = !it }, Icons.Default.MoreVert)
            }

            MediaLibraryContent(
                files = files,
                onDeleteMedia = { uriString ->
                    viewModel.removeFile(uriString, playlistLibraryViewModel = playlistLibraryViewModel)
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