package com.example.lusonus.data.model

import com.example.lusonus.data.model.classes.Profile
import com.example.lusonus.data.model.interfaces.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileRepository() : ProfileRepository {

    override suspend fun saveProfile(profileData: Profile) {
        TODO("Not yet implemented")
    }

    override suspend fun getProfile(): StateFlow<Profile> {
        TODO("Not yet implemented")
    }

    override suspend fun clear() {
        TODO("Not yet implemented")
    }

}