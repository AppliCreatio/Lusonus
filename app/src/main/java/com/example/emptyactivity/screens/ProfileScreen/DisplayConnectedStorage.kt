package com.example.emptyactivity.screens.ProfileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.List
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.twotone.List
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emptyactivity.screens.ProfileScreen.data_classes.ExternalStorage

/*
 This saver variation of ExternalStorage is meant as a conversion tool to be able to store the ExternalStorage data class
 as a state that can be remembered through recomposition. When something is saved, it creates a new object that mimics the data class
 and when we want to use it, it creates a new instance of said data class
*/
val ConnectedStorageSaver = Saver<ExternalStorage, List<Any>>(
    save = { listOf(it.name, it.isConnected) },
    restore = { ExternalStorage(it[0] as String, it[1] as Boolean) }
)

/**
 * A composable which is a container that displays the current storage that is connected to the application/profile
 * For now it just displays a storage, but in the future there will be an actual way to connect to a storage
 */
@Composable
fun ConnectedStorage(modifier: Modifier) {

    // used online recourses as well as AI to understand how to save a data class as a state
    // mostly gonna be used within the future when I add the option to change storages
    var connectedStorage = rememberSaveable(saver = ConnectedStorageSaver) {
        ExternalStorage(
            "MyNAS",
            true
        )
    }

    Column(
        modifier = modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            "Connected Storage", fontSize = 24.sp,
            modifier = Modifier
                .drawBehind { // draws the line at the bottom of the text composable

                    val strokeWidth = 0.5.dp.toPx() * density
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color.White,
                        Offset(0f, y),
                        Offset(size.width, y),
                        strokeWidth
                    )
                }
                .fillMaxWidth()
                .padding(3.dp)
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Icon(
                    Icons.AutoMirrored.TwoTone.List,
                    "Storage is connected",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(connectedStorage.name)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(if (connectedStorage.isConnected) "Connected" else "Disconnected")
                Icon(Icons.Rounded.CheckCircle, "Storage is connected", tint = Color.Green)
            }
        }
//        Button(onClick = {}) { Text("Other Storages") }
    }
}
