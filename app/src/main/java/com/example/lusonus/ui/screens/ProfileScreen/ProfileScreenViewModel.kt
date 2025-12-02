package com.example.lusonus.ui.screens.ProfileScreen


import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lusonus.data.auth.AuthRepository
import com.example.lusonus.data.model.classes.Profile
import com.example.lusonus.data.model.classes.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileScreenViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun currentUser(): StateFlow<User?> {
        return authRepository.currentUser()
    }

    private val _currentProfile = MutableStateFlow<Profile>(Profile())
    val currentProfile: StateFlow<Profile> get() = _currentProfile.asStateFlow()


    fun setProfileInfo(newProfile: Profile) {
        _currentProfile.update {
            it.copy(
                name = newProfile.name,
                description = newProfile.description
            )
        }
    }

    // Sets the profile picture for the user, I need this separate method since
    fun setPicture(picture: Uri) {
        _currentProfile.update { it.copy(image = picture) }
    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signOut()
        }

    }

    fun delete() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.delete()
        }
    }
}