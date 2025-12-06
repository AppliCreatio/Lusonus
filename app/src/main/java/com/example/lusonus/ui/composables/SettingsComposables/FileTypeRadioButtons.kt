package com.example.lusonus.ui.composables.SettingsComposables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.lusonus.ui.screens.SettingsScreen.SettingsViewModel

// Creating using template from https://developer.android.com/develop/ui/compose/components/radio-button
@Composable
fun RadioButtonSingleSelection(modifier: Modifier = Modifier, viewModel: SettingsViewModel) {
    val radioOptions = listOf("None", "MP3", "MP4")
    val selectedOption = remember { mutableIntStateOf(viewModel.settings.fileTypeRestriction) }
    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Text(radioOptions[selectedOption.intValue])

    Column(modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == radioOptions[selectedOption.intValue]),
                        onClick = {
                            onOptionSelected(radioOptions.indexOf(text), viewModel)
                            selectedOption.intValue = radioOptions.indexOf(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == radioOptions[selectedOption.intValue]),
                    onClick = null // null recommended for accessibility with screen readers
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

private fun ColumnScope.onOptionSelected(index: Int, viewModel: SettingsViewModel) {
    viewModel.changeFileTypeRestriction(index)
}
