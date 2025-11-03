package com.example.lusonus.ui.composables.MediaComposables.MediaPopUp

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.ui.composables.Layout.MainLayout

@Composable
fun MediaPopUpScreen() {
    val viewModel: MediaPopUpViewModel = viewModel(viewModelStoreOwner = LocalNavController.current.context as ComponentActivity)

    MainLayout(
        content = {
            MediaPopUpContent(viewModel.mediaName.value, viewModel.mediaArtist.value, viewModel.isPaused)
        },
        screenTitle = viewModel.mediaName.value
    )
}