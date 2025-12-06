package com.example.lusonus.ui.composables.RegisterScreenComposables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RegisterInputField(value: String, changeValue: (String)-> Unit, label: String, icon: ImageVector){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Your ${label.lowercase()}",
            tint = MaterialTheme.colorScheme.primary
        )
        OutlinedTextField(
            modifier = Modifier.padding(start = 10.dp,end = 34.dp),
            value = value,
            onValueChange = { changeValue(it) },
            visualTransformation = if(label == "Password") PasswordVisualTransformation() else VisualTransformation.None,
            label = { Text(text = label) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            singleLine = true
        )
    }
}