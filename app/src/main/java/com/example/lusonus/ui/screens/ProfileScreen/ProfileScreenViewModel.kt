package com.example.lusonus.ui.screens.ProfileScreen


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lusonus.data.model.Profile

class ProfileScreenViewModel : ViewModel() {

    var currentProfile = mutableStateOf<Profile>(Profile())
        private set

    fun getCurrentProfile(): Profile{
        return currentProfile.value
    }

    fun EditProfile(){

    }
}