package com.example.lusonus.ui.utils.Dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.lusonus.data.model.ExternalStorage
import com.example.lusonus.navigation.LocalStorageList

/*
*   Coded by Alex
*  */

@Composable
fun ConnectedStorageDialog(
    onRequest: () -> Unit,
    title: String,
    setStorage: (ExternalStorage) -> Unit,
) {
    val connectedStorages = LocalStorageList.current

    Dialog(onDismissRequest = { onRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier.padding(bottom = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                    ) {
                        Icon(Icons.TwoTone.Info, "Bad Register Icon")
                        Text(title, fontWeight = FontWeight.Bold)
                    }

                    LazyColumn(Modifier.height(100.dp)) {
                        items(connectedStorages) { item ->
                            StorageDisplay(item, setStorage, onRequest)
                        }
                    }
                }
            }
        }
    }
}

/**
 * This composable displays a button that shows the an available storage in the storage picker
 */
@Composable
fun StorageDisplay(
    storage: ExternalStorage,
    setStorage: (ExternalStorage) -> Unit,
    onRequest: () -> Unit,
) {
    val connectedStorages = LocalStorageList.current
    Button(
        onClick = {
            connectedStorages.remove(storage)
            setStorage(storage)
            onRequest()
        },
        modifier =
            Modifier
                .fillMaxWidth(),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onBackground,
            ),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(55.dp)) {
            Text(storage.name)
            Text(if (storage.isConnected) "Connected" else "Disconnected")
        }
    }
}
