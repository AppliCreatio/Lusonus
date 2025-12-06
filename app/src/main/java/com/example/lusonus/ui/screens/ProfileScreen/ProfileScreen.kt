package com.example.ass3_appdev.screens.main

import android.os.Build
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.ProfileComposables.ProfileBanner
import com.example.lusonus.ui.screens.ProfileScreen.ProfileScreenViewModel
import com.example.lusonus.ui.screens.ProfileScreen.ProfileViewModelFactory
import com.example.lusonus.ui.utils.Dialogs.BasicConfirmCancelDialog
import com.example.lusonus.ui.utils.Dialogs.DialogToEditProfile

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(viewModel: ProfileScreenViewModel = viewModel(factory = ProfileViewModelFactory())) {
    val navController = LocalNavController.current

    val userState = viewModel.currentUser().collectAsState()

    // Makes sure the user is logged in before being able to access the profile screen
    if (userState.value == null) {
        navController.navigate(Routes.Register.route)
    } else {
        val profile by viewModel.currentProfile.collectAsStateWithLifecycle()

        var openEditDialog by rememberSaveable { mutableStateOf(false) }
        var deleting by rememberSaveable { mutableStateOf(false) }
        var promptLogOut by rememberSaveable { mutableStateOf(false) }

        // When openEditDialog is true, it will display the edit profile dialog box
        if (openEditDialog) {
            DialogToEditProfile(
                onDismissRequest = { openEditDialog = false },
                onConfirmation = { openEditDialog = false },
                name = "",
                setProfile = { viewModel.setProfileInfo(it) },
                setPicture = { viewModel.setPicture(it ?: profile.image) },
            )
        }

        // This checks if the confirmation dialog box for deleting should appear on the screen or not
        if (deleting) {
            BasicConfirmCancelDialog(
                onDismissRequest = { deleting = false },
                onConfirmRequest = {
                    viewModel.delete()
                    deleting = false
                },
                title = "Account Deletion",
                description = "You're about to delete this account are you sure?",
                confirmString = "Delete",
            )
        }

        // This checks if the confirmation dialog box for logging should appear on the screen or not
        if (promptLogOut) {
            BasicConfirmCancelDialog(
                onDismissRequest = {
                    promptLogOut = false
                    deleting = false
                },
                onConfirmRequest = {
                    viewModel.signOut()
                    promptLogOut = false
                    deleting = false
                },
                title = "Logging Out",
                description = "You're about to log out are you sure?",
                confirmString = "Log Out",
            )
        }

        // General Container for other information from the profile screen
        val containerDisplay: Modifier =
            Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(10.dp),
                )

        MainLayout(
            {
                LazyColumn(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    item {
                        ProfileBanner(
                            modifier =
                                Modifier
                                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                                    .height(120.dp)
                                    .fillMaxWidth()
                                    .background(
                                        MaterialTheme.colorScheme.primaryContainer,
                                        shape = RoundedCornerShape(10.dp),
                                    ),
                            name = profile.name,
                            email = userState.value!!.email,
                            profileImage = profile.image,
                            editToggle = { openEditDialog = true },
                            signOut = { promptLogOut = true },
                            delete = { deleting = true },
                        )
                    }
                    item {
                        ConnectedStorage(
                            modifier =
                                containerDisplay
                                    .height(120.dp)
                                    .padding(bottom = 10.dp),
                        )
                    }
                }
            },
            screenTitle = "Profile",
            floatingActionButton = {},
        )
    }
}
