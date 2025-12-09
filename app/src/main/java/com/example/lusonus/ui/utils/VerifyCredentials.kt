package com.example.lusonus.ui.utils

/*
*   Coded by Alex
*  */

fun verifyCredentials(
    email: String,
    password: String,
    modErrorMessage: (String) -> Unit,
): Boolean {

    // Separated into different if statements to have a more specific error message
    var didPass = true

    if (email.trim().isEmpty()) {
        modErrorMessage("Your email is empty. ")
        didPass = false
    }

    if (password.trim().isEmpty()) {
        modErrorMessage("Your password is empty. ")
        didPass = false
    }

    return didPass
}
