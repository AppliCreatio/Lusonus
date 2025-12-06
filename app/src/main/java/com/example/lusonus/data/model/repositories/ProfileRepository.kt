package com.example.lusonus.data.model.repositories

import com.example.lusonus.data.dataclasses.Profile
import com.example.lusonus.data.interfaces.ProfileRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProfileRepository : ProfileRepositoryInterface {
    private val profile = MutableStateFlow(Profile())

    override suspend fun saveProfile(profileData: Profile) {
        profile.update {
            it.copy(
                name = profileData.name,
                image = profileData.image,
            )
        }
    }

    override suspend fun getProfile(): StateFlow<Profile> = profile

    override suspend fun clear() {
        TODO("Not yet implemented")
    }
}
