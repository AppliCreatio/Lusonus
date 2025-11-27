package com.example.lusonus.ui.composables.Layout.TopBar

import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lusonus.R
import com.example.lusonus.ui.composables.Layout.Buttons.MenuDropDown.MenuDropDown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTopBarStateless(
    screenTitle: String,
    canNavigateBack: Boolean,
    onNavigateBack: () -> Unit
) {
    // The top bar of the scaffold.
    CenterAlignedTopAppBar(
        // The colors of the top bar.
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),

        // The title of the top bar.
        title = {
            Text(
                text = screenTitle,
                style = MaterialTheme.typography.titleLarge
            )
        },

        // The navigation icon of the top bar, "go back" button.
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = {
                        onNavigateBack()
                    }
                ) {
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Previous Media.")
                }
            }
        },

//         The options menu (if needed)
        actions = {
            MenuDropDown()
        },
    )
}