package com.example.emptyactivity.screens.ProfileScreen.screens.register

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.emptyactivity.navigation.LocalNavController
import com.example.emptyactivity.R
import com.example.emptyactivity.navigation.Routes
import com.example.emptyactivity.screens.ProfileScreen.reusable_composables.MainLayout
import com.example.emptyactivity.screens.ProfileScreen.reusable_composables.dialogs.BadRegisterDialog

@Composable
fun RegisterScreen() {

    val navController = LocalNavController.current
    var newName by rememberSaveable { mutableStateOf("") }
    var newDescription by rememberSaveable { mutableStateOf("") }
    var profilePicture by rememberSaveable { mutableStateOf(Uri.EMPTY) }
    var badRegisterDialog by rememberSaveable { mutableStateOf(false) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> profilePicture = uri }
    )


    MainLayout({
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Text("Welcome to my app!", fontSize = 20.sp)
            }

            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                ) {

                    AsyncImage(
                        model = if (profilePicture != Uri.EMPTY && profilePicture != null) {
                            profilePicture
                        } else R.drawable.resource_default,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(256.dp)
                            .clip(CircleShape)
                            .border(10.dp, Color.LightGray, CircleShape)
                            .background(MaterialTheme.colorScheme.onBackground),
                    )

                    Button(onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) { Text("Profile Picture") }

                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("Name") },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = newDescription,
                        onValueChange = { newDescription = it },
                        label = { Text("Description") },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        singleLine = true
                    )


                    Button(onClick = {

                        if (!newName.trim().isEmpty() and !newDescription.trim().isEmpty()) {
                            navController.navigate(
                                Routes.Main.go(
                                    newName,
                                    newDescription,
                                    profilePicture
                                )
                            )

                            newName = ""
                            newDescription = ""
                            profilePicture = Uri.EMPTY
                        } else
                            badRegisterDialog = true
                    })
                    {
                        Text("Register")
                    }

                    if (badRegisterDialog)
                        BadRegisterDialog { badRegisterDialog = false }


                }
            }
        }

    }, {}, "My App")
}