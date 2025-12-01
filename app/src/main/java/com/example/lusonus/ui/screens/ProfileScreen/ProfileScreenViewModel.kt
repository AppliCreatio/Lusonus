package com.example.lusonus.ui.screens.ProfileScreen


import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lusonus.data.auth.AuthRepository
import com.example.lusonus.data.auth.ResultAuth
import com.example.lusonus.data.model.classes.Profile
import com.example.lusonus.data.model.classes.User
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileScreenViewModel : ViewModel() {

    private val _currentProfile = MutableStateFlow<Profile>(Profile())
    val currentProfile: StateFlow<Profile> get() =  _currentProfile.asStateFlow()


    fun setProfile(newProfile: Profile) {
//        _currentProfile.value.EditName(newProfile.name)
//        _currentProfile.value.EditDescription(newProfile.description)
//        _currentProfile.value.EditProfileImage(if(newProfile.image == Uri.EMPTY) _currentProfile.value.image else newProfile.image)

        _currentProfile.update {
            it.copy(
                name = newProfile.name,
                description = newProfile.description
            )
        }
    }

    fun setPicture(picture: Uri){
        _currentProfile.update{it.copy(image =picture)}
    }
}