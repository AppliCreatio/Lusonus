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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AlternateEmail
import androidx.compose.material.icons.sharp.Key
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
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
import com.example.lusonus.ui.composables.Layout.TopBar.SharedTopBarStateless
import com.example.lusonus.ui.screens.RegisterScreen.AuthViewModelFactory
import com.example.lusonus.ui.screens.ProfileScreen.ProfileScreenViewModel
import com.example.lusonus.ui.utils.Dialogs.BadRegisterDialog
import com.example.lusonus.ui.utils.verifyCredentials

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(viewModel: RegisterViewModel = viewModel(factory = AuthViewModelFactory())) {

    // This is used for the form
    val navController = LocalNavController.current
    var canNavigateBack = navController.previousBackStackEntry != null

    var newEmail by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var badRegisterDialog by rememberSaveable { mutableStateOf(false) }

    // This is used for register prompting
    val signUpResult by viewModel.signUpResult.collectAsState(ResultAuth.Inactive)
    val signInResult by viewModel.signInResult.collectAsState(ResultAuth.Inactive)
    var errorMessage = ""

    val snackbarHostState = remember { SnackbarHostState() } // Material 3 approach

    LaunchedEffect(signInResult) {
        signInResult?.let {
            when (it) {
                is ResultAuth.Inactive -> return@LaunchedEffect
                is ResultAuth.InProgress -> {
                    snackbarHostState.showSnackbar("Sign-in In Progress")
                }
                is ResultAuth.Success -> {
                    if (it.data) {
                        snackbarHostState.showSnackbar("Sign-in Successful")
                        navController.navigateUp()
                    } else {
                        snackbarHostState.showSnackbar("Sign-in Unsuccessful: ${viewModel.errorMessage}")
                    }
                }
                is ResultAuth.Failure -> {
                    snackbarHostState.showSnackbar("Sign-in Failed: ${it.exception.message}")
                }
            }
        }
    }

    LaunchedEffect(signUpResult) {
        signUpResult?.let {
            when (it) {
                is ResultAuth.Inactive -> return@LaunchedEffect
                is ResultAuth.InProgress -> {
                    snackbarHostState.showSnackbar("Sign-up In Progress")
                }
                is ResultAuth.Success -> {
                    if (it.data) {
                        snackbarHostState.showSnackbar("Sign-up Successful")
                        navController.navigateUp()
                    } else {
                        snackbarHostState.showSnackbar("Sign-up Unsuccessful: ${viewModel.errorMessage}")
                    }
                }
                is ResultAuth.Failure -> {
                    snackbarHostState.showSnackbar("Sign-up Failed: ${it.exception.message}")
                }
            }
        }
    }

    MainLayout({
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Card(colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)) {
                Text("Register", fontSize = 20.sp)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                ) {

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Sharp.AlternateEmail,
                            contentDescription = "Your email",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        OutlinedTextField(
                            value = newEmail,
                            onValueChange = { newEmail = it },
                            label = { Text("Email") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.background,
                                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            ),
                            singleLine = true
                        )
                    }


                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Sharp.Key,
                            contentDescription = "Your email",
                            tint = MaterialTheme.colorScheme.primary
                        )

                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            label = { Text("Password") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.background,
                                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            ),
                            singleLine = true
                        )
                    }

                    Row {
                        Button(onClick = {
                            errorMessage = ""
                            if (verifyCredentials(
                                    email = newEmail,
                                    password = newPassword,
                                    modErrorMessage = { errorMessage += it })
                            )
                                viewModel.signIn(newEmail, newPassword)
                            else
                                badRegisterDialog = true
                        })
                        {
                            Text("Sign In")
                        }

                        Button(onClick = {
                            errorMessage = ""
                            if (verifyCredentials(
                                    email = newEmail,
                                    password = newPassword,
                                    modErrorMessage = { errorMessage += it })
                            )
                                viewModel.signUp(newEmail, newPassword)
                            else
                                badRegisterDialog = true
                        })
                        {
                            Text("Sign Up")
                        }
                    }


                    if (badRegisterDialog)
                        BadRegisterDialog(
                            onDismissRequest = { badRegisterDialog = false },
                            errorMessage
                        )


                }
            }
        }
    },
        screenTitle = "Register",
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            SharedTopBarStateless(
                canNavigateBack = canNavigateBack,
                pageName = "Register",
                actionButton = {},
                onNavigateBack = {
                    if (canNavigateBack){
                        navController.popBackStack()
                        navController.popBackStack()
                    }
                },
                showDropDown = false
            )
        }
    )
}

