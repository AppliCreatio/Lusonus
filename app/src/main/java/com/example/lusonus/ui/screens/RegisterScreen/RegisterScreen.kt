package com.example.lusonus.ui.screens.RegisterScreen

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.R
import com.example.lusonus.data.auth.ResultAuth
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.screens.RegisterScreen.AuthViewModelFactory
import com.example.lusonus.ui.screens.ProfileScreen.ProfileScreenViewModel
import com.example.lusonus.ui.utils.Dialogs.BadRegisterDialog

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(viewModel: RegisterViewModel = viewModel(factory = AuthViewModelFactory())) {

    // This is used for the form
    val navController = LocalNavController.current
    var newName by rememberSaveable { mutableStateOf("") }
    var newEmail by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var newDescription by rememberSaveable { mutableStateOf("") }
    var profilePicture by rememberSaveable { mutableStateOf(Uri.EMPTY) }
    var badRegisterDialog by rememberSaveable { mutableStateOf(false) }

    // This is used for register prompting
    val signUpResult by viewModel.signUpResult.collectAsState(ResultAuth.Inactive)
    val signInResult by viewModel.signInResult.collectAsState(ResultAuth.Inactive)
    var errorMessage = ""

    // Launcher for getting images
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> profilePicture = uri }
    )

    val snackbarHostState = remember { SnackbarHostState() } // Material 3 approach

    LaunchedEffect(signInResult) {
        signInResult?.let {
            if (it is ResultAuth.Inactive) {
                return@LaunchedEffect
            }
            if (it is ResultAuth.InProgress) {
                snackbarHostState.showSnackbar("Sign-in In Progress")
                return@LaunchedEffect
            }
            if (it is ResultAuth.Success && it.data) {
                snackbarHostState.showSnackbar("Sign-in Successful")
                navController.navigate(
                    Routes.Profile.go(
                        newName,
                        newDescription,
                        profilePicture
                    )
                )
            } else if (it is ResultAuth.Failure || it is ResultAuth.Success) { // success(false) case
                snackbarHostState.showSnackbar("Sign-in Unsuccessful")
            }
        }
    }

    LaunchedEffect(signUpResult) {
        signUpResult?.let {
            if (it is ResultAuth.Inactive) {
                return@LaunchedEffect
            }
            if (it is ResultAuth.InProgress) {
                snackbarHostState.showSnackbar("Sign-up In Progress")
                return@LaunchedEffect
            }
            if (it is ResultAuth.Success && it.data) {
                snackbarHostState.showSnackbar("Sign-up Successful")
            } else if (it is ResultAuth.Failure || it is ResultAuth.Success) { // success(false) case
                snackbarHostState.showSnackbar("Sign-up Unsuccessful")
            }
        }
    }



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

                    Image(
                        painter = painterResource(id = R.drawable.lusonus_placeholder),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(256.dp)
                            .clip(CircleShape)
                            .border(10.dp, Color.LightGray, CircleShape)
                            .background(MaterialTheme.colorScheme.onBackground)
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

                    OutlinedTextField(
                        value = newEmail,
                        onValueChange = { newEmail = it },
                        label = { Text("Email") },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("Password") },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        singleLine = true
                    )

                    Button(onClick = {

                        if(newEmail.trim().isNotEmpty() and newPassword.trim().isNotEmpty())
                            viewModel.signIn(newEmail, newPassword)

                            if(signUpResult)
                        else {
                            errorMessage += "Your email and/or password are invalid. Try Again"
                            badRegisterDialog = true
                        }


                        if (newName.trim().isNotEmpty() and newDescription.trim().isNotEmpty()) {


                            newName = ""
                            newDescription = ""
                            profilePicture = Uri.EMPTY
                        } else{
                            errorMessage = "Your name and/or description are empty. Try Again"
                            badRegisterDialog = true

                        }
                    })
                    {
                        Text("Register")
                    }

                    if (badRegisterDialog)
                        BadRegisterDialog(onDismissRequest = {badRegisterDialog = false}, errorMessage)


                }
            }
        }

    }, screenTitle = "Register")
}