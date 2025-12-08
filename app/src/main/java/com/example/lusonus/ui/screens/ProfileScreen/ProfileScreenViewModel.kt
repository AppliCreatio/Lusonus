package com.example.lusonus.ui.screens.ProfileScreen

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lusonus.appContext
import com.example.lusonus.data.auth.AuthRepository
import com.example.lusonus.data.dataclasses.Profile
import com.example.lusonus.data.dataclasses.User
import com.example.lusonus.data.dataclasses.proto.ProfileProto
import com.example.lusonus.data.dataclasses.proto.Settings
import com.example.lusonus.data.dataclasses.protodatastore.profileDataStore
import com.example.lusonus.data.dataclasses.protodatastore.settingsDataStore
import com.example.lusonus.data.interfaces.ProfileRepositoryInterface
import com.example.lusonus.data.sharedinstances.Global
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepositoryInterface,
) : ViewModel() {

    var profile by mutableStateOf(ProfileProto.getDefaultInstance())
        private set

    fun currentUser(): StateFlow<User?> = authRepository.currentUser()

    private val _currentProfile = MutableStateFlow<Profile>(value = Profile())
    val currentProfile: StateFlow<Profile> get() = _currentProfile.asStateFlow()

    init {
        viewModelScope.launch {
            val proto = appContext.profileDataStore.data.first()
            profile = proto

            // Load profile when ViewModel is created
            loadProfile(profile)
        }
    }

    // Loads the user information
    private fun loadProfile(proto: ProfileProto) {
        viewModelScope.launch {
            appContext.profileDataStore.data.collect { proto ->
                _currentProfile.value = Profile(
                    name = proto.name,
                    image = if (proto.image.isNotEmpty()) proto.image.toUri() else Uri.EMPTY
                )
            }
        }
    }

    fun setProfileInfo(newProfile: Profile) =
        viewModelScope.launch {
            val updated = profile.toBuilder()
                .setName(newProfile.name)
                .build()
            profile = updated

            persist(updated)

            _currentProfile.update {
                it.copy(name = profile.name)
            }

            profileRepository.saveProfile(_currentProfile.value)

        }


    // Sets the profile picture for the user, I need this separate method since
    fun setPicture(picture: Uri) =
        viewModelScope.launch {
            val updated = profile.toBuilder()
                .setImage(picture.toString())
                .build()
            profile = updated

            persist(updated)

            _currentProfile.update { it.copy(image = picture) }

            profileRepository.saveProfile(_currentProfile.value)


        }


    fun signOut() =
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signOut()
        }


    fun delete() =
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.delete()
        }


    private suspend fun persist(value: ProfileProto) {
        appContext.profileDataStore.updateData { value }
    }
}
