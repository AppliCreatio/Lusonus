package com.example.lusonus.data.auth

import com.example.lusonus.data.dataclasses.User
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    // Return a StateFlow so that the composable can always update when
    //   the current authorized user status changes for any reason
    fun currentUser(): StateFlow<User?>

    suspend fun signUp(email: String, password: String): String?

    suspend fun signIn(email: String, password: String): String?

    fun signOut()

    suspend fun delete()

}