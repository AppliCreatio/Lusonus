package com.example.lusonus.ui.utils

fun verifyCredentials(email: String, password: String, modErrorMessage: (String) -> Unit): Boolean{

    var didPass = true

    if(email.trim().isEmpty())
    {
        modErrorMessage("Your email is empty. ")
        didPass =  false
    }

    if(password.trim().isEmpty())
    {
        modErrorMessage("Your password is empty. ")
        didPass = false
    }

    return didPass
}