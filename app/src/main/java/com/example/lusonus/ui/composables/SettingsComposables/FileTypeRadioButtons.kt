package com.example.lusonus.ui.composables.SettingsComposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.lusonus.ui.screens.SettingsScreen.SettingsViewModel

// Creating using template from https://developer.android.com/develop/ui/compose/components/radio-button
@Composable
fun RadioButtonSingleSelection(
    viewModel: SettingsViewModel,
) {
    val radioOptions = listOf("None", "MP3", "MP4")
    val selectedIndex = viewModel.settings.fileTypeRestriction

    Card() { }
    Column(Modifier.selectableGroup(), horizontalAlignment = Alignment.CenterHorizontally) {
        radioOptions.forEachIndexed { index, text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (index == selectedIndex),
                        onClick = { viewModel.changeFileTypeRestriction(index) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = (index == selectedIndex),
                    onClick = null
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }

}

private fun ColumnScope.onOptionSelected(
    index: Int,
    viewModel: SettingsViewModel,
) {
    viewModel.changeFileTypeRestriction(index)
}
