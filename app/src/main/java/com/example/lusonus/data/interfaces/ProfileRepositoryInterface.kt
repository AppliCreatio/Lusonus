package com.example.lusonus.data.interfaces

import com.example.lusonus.data.dataclasses.Profile
import kotlinx.coroutines.flow.StateFlow

/*
*   Alex made this entire file
*  */

interface ProfileRepositoryInterface {
    suspend fun saveProfile(profileData: Profile)

    suspend fun getProfile(): StateFlow<Profile>

    suspend fun clear()
}
