package com.example.lusonus.ui.screens.RegisterScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.material.icons.sharp.AlternateEmail
import androidx.compose.material.icons.sharp.Key
import androidx.compose.material.icons.twotone.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.data.auth.ResultAuth
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.Layout.TopBar.SharedTopBarStateless
import com.example.lusonus.ui.composables.RegisterScreenComposables.RegisterBanner
import com.example.lusonus.ui.composables.RegisterScreenComposables.RegisterButtons
import com.example.lusonus.ui.composables.RegisterScreenComposables.RegisterInputField
import com.example.lusonus.ui.composables.RegisterScreenComposables.RegisterSnackbar
import com.example.lusonus.ui.screens.RegisterScreen.AuthViewModelFactory
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
    var newName by rememberSaveable { mutableStateOf("") }
    var badRegisterDialog by rememberSaveable { mutableStateOf(false) }

    // This is used for register prompting
    val signUpResult by viewModel.signUpResult.collectAsState(ResultAuth.Inactive)
    val signInResult by viewModel.signInResult.collectAsState(ResultAuth.Inactive)
    var errorMessage = ""

    val snackbarHostState = remember { SnackbarHostState() } // Material 3 approach


    // This launched effect checks for any new results being assigned in the viewmodel and it is used to
    // display any messages that is sent by FirebaseAuthentication. This one checks for Sign In Results
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

    // This launched effect checks for any new results being assigned in the viewmodel and it is used to
    // display any messages that is sent by FirebaseAuthentication. This one checks for Sign Up Results
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

    MainLayout(
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .padding(bottom = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround,
                    ) {

                        RegisterBanner()

                        // All of the input fields for account handling
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            RegisterInputField(value = newName, changeValue = { newName = it}, label = "Username", icon = Icons.Sharp.AccountCircle)
                            RegisterInputField(value = newEmail, changeValue = { newEmail = it}, label = "Email", icon = Icons.Sharp.AlternateEmail)
                            RegisterInputField(value = newPassword, changeValue = { newPassword = it}, label = "Password", icon = Icons.Sharp.Key)
                        }

                        // The buttons for sign up and sign in
                        RegisterButtons(newEmail, newPassword, {errorMessage = it}, viewModel, {badRegisterDialog = true})

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
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) { data ->

                // This is how we show the firebase messages on the screen
                RegisterSnackbar(data)
            }
        },
        topBar = {

            // Different top bar since the back button logic is different from the default logic
            // as well as we do not want the user to go to any other screen
            SharedTopBarStateless(
                canNavigateBack = canNavigateBack,
                pageName = "Register",
                actionButton = {},
                onNavigateBack = {
                    if (canNavigateBack) {
                        navController.popBackStack()
                        navController.popBackStack()
                    }
                },
                showDropDown = false,
            )
        },
    )
}
