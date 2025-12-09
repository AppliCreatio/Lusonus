package com.example.lusonus.ui.screens.SettingsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lusonus.navigation.LocalSettingsViewModel
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.SettingsComposables.RadioButtonSingleSelection

/*
*   Aris made this file with changes made by Brandon
*  */

@Composable
fun SettingScreen() {
    val viewModel = LocalSettingsViewModel.current

    MainLayout({
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Disable Profile")

            Box(Modifier.padding(16.dp))
            {
                Card(
                    colors =
                        CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = MaterialTheme.shapes.medium,
                    modifier =
                        Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth(),
                )
                {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Switch(
                            checked = viewModel.settings.profileToggle,
                            onCheckedChange = {
                                viewModel.toggleProfile()
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Text("Restrict File Type")
            Box(Modifier.padding(16.dp))
            {
                Card(
                    colors =
                        CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = MaterialTheme.shapes.medium,
                    modifier =
                        Modifier
                            .padding(bottom = 16.dp),
                )
                {
                    RadioButtonSingleSelection(viewModel)
                }
            }
        }
    }, "Settings")
}
