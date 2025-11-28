package com.example.lusonus.ui.screens.ProfileScreen


import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.lusonus.data.model.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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