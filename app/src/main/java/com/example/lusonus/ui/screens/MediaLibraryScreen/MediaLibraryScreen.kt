package com.example.lusonus.ui.screens.MediaLibraryScreen

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.data.model.MenuItem
import com.example.organisemedia.Layout.FloatingActionButton.SharedFloatingActionButton
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes
import com.example.lusonus.ui.composables.Layout.Buttons.MenuDropDown.MinimalDropdownMenu
import com.example.lusonus.ui.screens.PlaylistLibraryScreen.PlaylistLibraryViewModel

@Composable
fun MediaLibraryScreen() {
    // Gets nav controller
    val navController = LocalNavController.current

    // Gets the view model information
    val viewModel: MediaLibraryViewModel = viewModel(viewModelStoreOwner = LocalNavController.current.context as ComponentActivity) // Gets an existing MediaViewModel if it exists.

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

//    var expanded by remember { mutableStateOf(false) }
//    var searchInfo by rememberSaveable { mutableStateOf("") }

    MainLayout(
        content = {

//            val sortOptions = listOf<MenuItem>(
//                MenuItem("Alphabetical") { viewModel.sortFiles("alphabetically", context) }
//            )

//            Row(modifier = Modifier.fillMaxWidth()) {
//                MinimalDropdownMenu(sortOptions, expanded, { expanded = !it }, Icons.Sharp.Menu)
//
////                OutlinedTextField(
////                    value = searchInfo,
////                    onValueChange = {
////                        searchInfo = it
////                        viewModel.searchMedia(context, searchInfo.lowercase())
////                    },
////                    label = "Description" ,
////                    colors = TextFieldDefaults.colors(
////                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
////                    ),
////                    singleLine = true
////                )
//
//            }

            MediaLibraryContent(
                files = files,
                onDeleteMedia = { uri ->
                    viewModel.removeFile(uri)
                },
                onClickMedia = { mediaName ->
                    navController.navigate(Routes.MediaPlayer.go(mediaName))
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