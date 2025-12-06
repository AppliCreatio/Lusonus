package com.example.lusonus.ui.screens.ProfileScreen


import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lusonus.data.auth.AuthRepository
import com.example.lusonus.data.dataclasses.Profile
import com.example.lusonus.data.dataclasses.User
import com.example.lusonus.data.interfaces.ProfileRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepositoryInterface
) : ViewModel() {

    fun currentUser(): StateFlow<User?> {
        return authRepository.currentUser()
    }

    private val _currentProfile = MutableStateFlow<Profile>(value = Profile())
    val currentProfile: StateFlow<Profile> get() = _currentProfile.asStateFlow()

    init {
        // Load profile when ViewModel is created
        loadProfile()
    }

    // Loads the user information
    private fun loadProfile() {
        viewModelScope.launch {
            val savedProfile = profileRepository.getProfile().first()
            _currentProfile.value = savedProfile
        }
    }

    fun setProfileInfo(newProfile: Profile) {

        _currentProfile.update {
            it.copy(name = newProfile.name)
        }

        viewModelScope.launch {
            profileRepository.saveProfile(_currentProfile.value)
        }
    }

    // Sets the profile picture for the user, I need this separate method since
    fun setPicture(picture: Uri) {
        _currentProfile.update { it.copy(image = picture) }

        viewModelScope.launch {
            profileRepository.saveProfile(_currentProfile.value)
        }
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