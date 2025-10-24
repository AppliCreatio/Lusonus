package com.example.ass3_appdev.reusable_composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.emptyactivity.screens.ProfileScreen.LocalNavController
import com.example.emptyactivity.Routes
import com.example.ass3_appdev.reusable_composables.dialogs.BasicConfirmCancelDialog

/**
 * A reusable layout composable that I apply to every screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    content: @Composable () -> Unit,
    option: @Composable () -> Unit,
    screenTitle: String
) {
    // Added for log out functionality
    val navController = LocalNavController.current
    var logoutPrompt by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle) },
                navigationIcon = option
            )
        },
        floatingActionButton = {

            if (navController.currentBackStackEntry?.destination?.route != Routes.Register.route) {
                FloatingActionButton(onClick = { logoutPrompt = true }) {
                    Icon(imageVector = Icons.TwoTone.Lock, contentDescription = "Logout button")
                }
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            content()
            if (logoutPrompt)
                BasicConfirmCancelDialog(
                    onDismissRequest = { logoutPrompt = false },
                    onConfirmRequest = {
                        logoutPrompt = false
                        navController.popBackStack(Routes.Register.route, false)
                    },
                    title = "Logging Out",
                    description = "You are about to logout, are you sure you want to proceed?",
                    confirmString = "Logout"
                )
        }
    }

}
