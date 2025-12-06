package com.example.lusonus.ui.screens.SettingsScreen

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.navigation.LocalGlobals
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.SettingsComposables.RadioButtonSingleSelection

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingScreen() {
    val viewModel: SettingsViewModel =
        viewModel(
            viewModelStoreOwner = LocalNavController.current.context as ComponentActivity,
            factory = SettingsViewModelFactory(LocalGlobals.current.settings),
        ) // Gets an existing MediaViewModel if it exists/
    var profileToggleCheck by remember { mutableStateOf(viewModel.settings.profileToggle) }

    MainLayout({
        Column(modifier = Modifier) {
            Text("Disable Profile")
            Switch(
                checked = profileToggleCheck,
                onCheckedChange = {
                    viewModel.toggleProfile()
                    profileToggleCheck = viewModel.settings.profileToggle
                },
            )

            Text("Restrict File Type")
            RadioButtonSingleSelection(modifier = Modifier, viewModel)
        }
    }, "Settings")
}
