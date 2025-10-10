package com.example.filedisplayer

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri


// This is the screen where all imported files are shown/added
@OptIn(ExperimentalMaterial3Api::class) // This is required to use TopAppBar and etc.
@Composable
fun FileImportScreen() {
    // This is a list of "pointers" (Uri) to files.
    // We will use this to get file information to display/use.
    // This was super annoying, basically since mutableStateListOf is not supported by
    // rememberSaveable, I had to use a Saver.
    val files = rememberSaveable(
        saver = listSaver(
            save = { it.toList() }, // Converts it to a List<String> for saving
            restore = { mutableStateListOf(*it.toTypedArray()) } // Converts it back to the original
        )
    ) { mutableStateListOf<String>() }

    // The way to get access to android system services.
    val context = LocalContext.current

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
                files.addAll(
                    uris.map {
                        it.toString()
                    }
                )
            }
        }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = "File Import",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                // Column to center the button :p
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Button to open file selection menu
                    Button(onClick = {
                        // This is weird. Basically "audio/*" is for all files
                        // that's audio but with any subtype. (e.g. mp3, wav, etc.)
                        pickFilesLauncher.launch(arrayOf("audio/*"))
                    }) {
                        Text(text = "Select Files")
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Lazy column in-case can have a lot of files added
            LazyColumn {
                items(files) { uriString ->
                    // Have to re-parse it to an uri
                    val uri = uriString.toUri()
                    FileRow(uri)
                }
            }
        }
    }
}

@Composable
fun FileRow(uri: Uri) {
    // The way to get access to android system services.
    val context = LocalContext.current

    val name = remember(uri) {
        // Sets it to Unknown if it can't get the name... error handling basically.
        runCatching {
            context.getFileName(uri)
        }.getOrElse { "Unknown" }
    }

    // Displays file information.
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = name, style = MaterialTheme.typography.bodyLarge)
    }
}
