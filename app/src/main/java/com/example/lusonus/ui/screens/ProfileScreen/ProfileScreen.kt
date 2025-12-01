package com.example.ass3_appdev.screens.main

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.ProfileComposables.ProfileBanner
import com.example.lusonus.ui.screens.ProfileScreen.ProfileScreenViewModel
import com.example.lusonus.ui.utils.Dialogs.DialogToEditProfile

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen() {

    val viewModel: ProfileScreenViewModel = viewModel(viewModelStoreOwner = LocalNavController.current.context as ComponentActivity) // Gets an existing MediaViewModel if it exists.

    val profile by viewModel.currentProfile.collectAsStateWithLifecycle()

    var openEditDialog by rememberSaveable { mutableStateOf(false) }

    // When openEditDialog is true, it will display the edit profile dialog box
    if (openEditDialog)
        DialogToEditProfile(
            { openEditDialog = false },
            { openEditDialog = false },
            profile.name,
            profile.description,
            { viewModel.setProfile(it) },
            { viewModel.setPicture(it ?: profile.image) })

    // General Container for other information from the profile screen
    val containerDisplay: Modifier = Modifier
        .padding(horizontal = 10.dp)
        .fillMaxWidth()
        .background(
            MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(10.dp)
        )

    MainLayout({
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            item {
                ProfileBanner(
                    modifier = Modifier
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                        .height(120.dp)
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.tertiaryContainer,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    profile.name, profile.description, profile.image, {openEditDialog = true}
                )
            }
            item {
                ConnectedStorage(
                    modifier = containerDisplay
                        .height(120.dp)
                        .padding(bottom = 10.dp)
                )
            }
        }
    }, "Profile")
}