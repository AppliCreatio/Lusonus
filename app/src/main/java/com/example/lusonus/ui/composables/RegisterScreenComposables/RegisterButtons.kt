package com.example.lusonus.ui.composables.RegisterScreenComposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.lusonus.ui.screens.RegisterScreen.RegisterViewModel
import com.example.lusonus.ui.utils.verifyCredentials

/*
*   Alex made this entire file
*  */

@Composable
fun RegisterButtons(
    email: String,
    password: String,
    createErrorMessage: (String) -> Unit,
    viewModel: RegisterViewModel,
    badInput: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(0.7f),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {
            var errorMessage = ""
            if (verifyCredentials(
                    email = email,
                    password = password,
                    modErrorMessage = { errorMessage += it })
            ) {

                viewModel.signIn(email, password)
            } else {
                createErrorMessage(errorMessage)
                badInput()
            }
        })
        {
            Text("Sign In")
        }

        TextButton(onClick = {
            var errorMessage = ""
            if (verifyCredentials(
                    email = email,
                    password = password,
                    modErrorMessage = { errorMessage += it })
            ) {
                viewModel.signUp(email, password)
            } else {
                createErrorMessage(errorMessage)
                badInput()

            }
        })
        {
            Text("Sign Up", color = MaterialTheme.colorScheme.onBackground)
        }
    }

}