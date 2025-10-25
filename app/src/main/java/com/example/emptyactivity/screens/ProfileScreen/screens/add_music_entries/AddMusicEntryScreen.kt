package com.example.emptyactivity.screens.ProfileScreen.screens.add_music_entries

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.emptyactivity.navigation.LocalNavController
import com.example.emptyactivity.navigation.LocalPlaylistList
import com.example.emptyactivity.navigation.LocalSongList
import com.example.emptyactivity.R
import com.example.emptyactivity.screens.ProfileScreen.classes.MusicEntryTypes
import com.example.emptyactivity.screens.ProfileScreen.classes.data_classes.MusicEntry
import com.example.emptyactivity.screens.ProfileScreen.reusable_composables.BackButton
import com.example.emptyactivity.screens.ProfileScreen.reusable_composables.MainLayout
import com.example.emptyactivity.screens.ProfileScreen.reusable_composables.dialogs.BadRegisterDialog

/**
 * A composable that displays the music entry adding screen, it takes in a musicEntryType to
 * figure which entry to create. The argument is a string and not an enum due to routing and navigation.
 * It is a stateful component due to it needed to remember the state of the variable when entering values in the
 * textboxes.
 */
@Composable
fun AddMusicEntryScreen(musicEntryType: String) {

    val navController = LocalNavController.current


    val type = MusicEntryTypes.valueOf(musicEntryType)
    val entryList = when (type) {
        MusicEntryTypes.SONG -> LocalSongList.current
        MusicEntryTypes.PLAYLISTS -> LocalPlaylistList.current
    }
    var entryName by rememberSaveable { mutableStateOf("") }
    var entryDescription by rememberSaveable { mutableStateOf("") }
    var entryPicture by rememberSaveable { mutableStateOf(Uri.EMPTY) }
    var badInputDialog by rememberSaveable { mutableStateOf(false) }

    // creates a rememberable picture launcher that shows all local pictures on a device
    // after selecting one, it gets the uri and sets the picture in the application.
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> entryPicture = uri }
    )

    MainLayout({
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                ) {

                    AsyncImage(
                        model = if (entryPicture != Uri.EMPTY && entryPicture != null) {
                            entryPicture
                        } else R.drawable.music_default_image,
                        contentDescription = "The cover for the music entry",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(256.dp)
                            .clip(RoundedCornerShape(7.dp))
                            .border(10.dp, Color.LightGray, RoundedCornerShape(7.dp))
                            .background(MaterialTheme.colorScheme.onBackground),
                    )

                    // Launches the picture picker
                    Button(onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) { Text("Entry Picture") }

                    OutlinedTextField(
                        value = entryName,
                        onValueChange = { entryName = it },
                        label = { Text("Name") },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = entryDescription,
                        onValueChange = { entryDescription = it },
                        label = { Text("Author") },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        singleLine = true
                    )


                    Button(onClick = {

                        // Checks whether or not the inputs are empty, if they are add them else, bad input
                        if (!entryName.trim().isEmpty() and !entryDescription.trim().isEmpty()) {

                            entryList.add(
                                MusicEntry(
                                    entryName,
                                    entryPicture,
                                    entryDescription,
                                    type
                                )
                            )

                            navController.popBackStack()
                        } else
                            badInputDialog = true
                    })
                    {
                        Text("Add")
                    }

                    if (badInputDialog)
                        BadRegisterDialog { badInputDialog = false }
                }
            }
        }

    }, { BackButton() }, "Add Song")
}
