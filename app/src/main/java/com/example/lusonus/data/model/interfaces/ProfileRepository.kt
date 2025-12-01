package com.example.lusonus.data.model.interfaces

import com.example.lusonus.data.model.classes.Profile
import kotlinx.coroutines.flow.Flow
interface ProfileRepository {
    suspend fun saveProfile(profileData: Profile)
    suspend fun getProfile(): Flow<Profile>
    suspend fun clear()
}
