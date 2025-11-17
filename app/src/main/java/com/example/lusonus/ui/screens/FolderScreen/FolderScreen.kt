package com.example.lusonus.ui.screens.FolderScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.data.model.MenuItem
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.Layout.SearchAndSort.SearchAndSort

@Composable
fun FolderScreen(folderName: String) {
    val navController = LocalNavController.current
    val viewModel: FolderViewModel = viewModel(factory = FolderViewModelFactory(folderName))

    val folderFiles = viewModel.folderFiles
    val allMediaFiles = viewModel.allMediaFiles

    var expanded by remember { mutableStateOf(false) }
    var searchInfo by rememberSaveable { mutableStateOf("") }

    MainLayout(
        content = {

            val sortOptions = listOf(
                MenuItem("Alphabetical") { viewModel.sortMedia("alphabetically") }
            )

            SearchAndSort(
                sortOptions, expanded, { expanded = !it }, searchInfo, {
                    searchInfo = it
                    viewModel.searchMedia(it.lowercase())
                }
            )

            FolderContent(
                folderFiles = folderFiles,
                onClickMedia = { mediaName ->
                    navController.navigate(Routes.MediaPlayer.go(mediaName))
                }
            )
        },

        screenTitle = folderName
    )
}
