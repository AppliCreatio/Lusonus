package com.example.lusonus.data.model

import com.example.lusonus.data.model.classes.Profile
import com.example.lusonus.data.model.interfaces.ProfileRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProfileRepository() : ProfileRepositoryInterface {

    private val profile =  MutableStateFlow(Profile())

    override suspend fun saveProfile(profileData: Profile) {
        profile.update {
            it.copy(
                name = profileData.name
            )
        }
    }

    override suspend fun getProfile(): StateFlow<Profile> {
        return profile
    }

    override suspend fun clear() {
        TODO("Not yet implemented")
    }

}