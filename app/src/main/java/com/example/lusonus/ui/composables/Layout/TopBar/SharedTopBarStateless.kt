package com.example.lusonus.ui.composables.Layout.TopBar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.lusonus.ui.composables.Layout.Buttons.MenuDropDown.MenuDropDown
import com.example.lusonus.ui.utils.fadeBottomEdge

/*
*   Brandon made this entire file
*  */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTopBarStateless(
    pageName: String,
    canNavigateBack: Boolean,
    onNavigateBack: () -> Unit,
    actionButton: @Composable (() -> Unit)? = null,
    showDropDown: Boolean = true,
) {
    var isNavigatingBack by rememberSaveable { mutableStateOf(false) }

    // The top bar of the scaffold.
    CenterAlignedTopAppBar(
        modifier =
            Modifier
                .height(96.dp)
                .fadeBottomEdge(
                    fadeHeight = 12.dp,
                    color = MaterialTheme.colorScheme.background,
                ),
        colors =
            topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                actionIconContentColor = MaterialTheme.colorScheme.onSurface,
            ),
        title = {
            Text(
                text = pageName,
                style =
                    MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (canNavigateBack && !isNavigatingBack) {
                IconButton(onClick = {
                    isNavigatingBack = true
                    onNavigateBack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Go back",
                    )
                }
            }
        },
        actions = {
            Row {
                actionButton?.invoke()
                if (showDropDown) {
                    MenuDropDown()
                }
            }
        },
    )
}
