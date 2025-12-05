package com.example.lusonus.data.model.interfaces

import com.example.lusonus.data.model.classes.Profile
import kotlinx.coroutines.flow.StateFlow

interface ProfileRepositoryInterface {
    suspend fun saveProfile(profileData: Profile)
    suspend fun getProfile(): StateFlow<Profile>
    suspend fun clear()
}
