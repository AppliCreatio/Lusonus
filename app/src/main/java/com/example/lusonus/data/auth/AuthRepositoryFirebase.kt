package com.example.lusonus.data.auth

import com.example.lusonus.data.dataclasses.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class AuthRepositoryFirebase(
    private val auth: FirebaseAuth,
) : AuthRepository {
    private val currentUserStateFlow = MutableStateFlow(auth.currentUser?.toUser())

    init {
        auth.addAuthStateListener { firebaseAuth ->
            currentUserStateFlow.value = firebaseAuth.currentUser?.toUser()
        }
    }

    // Returns the current user signed in
    override fun currentUser(): StateFlow<User?> = currentUserStateFlow

    /* Creates an account in the firebase with the given password and email if the email does not
     * already exist within the firebase */
    override suspend fun signUp(
        email: String,
        password: String,
    ): String? {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            return null
        } catch (e: FirebaseAuthException) {
            return when (e.errorCode) {
                "ERROR_INVALID_EMAIL" -> "Invalid email address"
                "ERROR_WEAK_PASSWORD" -> "Password is too weak"
                "ERROR_EMAIL_ALREADY_IN_USE" -> "Email is already registered"
                "ERROR_USER_NOT_FOUND" -> "User not found"
                "ERROR_WRONG_PASSWORD" -> "Incorrect password"
                else -> e.message ?: "Authentication failed"
            }
        }
    }

    // Signs the user into firebase using the provided email and password they inputted
    override suspend fun signIn(
        email: String,
        password: String,
    ): String? {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            return null
        } catch (e: FirebaseAuthException) {
            return when (e.errorCode) {
                "ERROR_INVALID_EMAIL" -> "Invalid email address"
                "ERROR_WEAK_PASSWORD" -> "Password is too weak"
                "ERROR_EMAIL_ALREADY_IN_USE" -> "Email is already registered"
                "ERROR_USER_NOT_FOUND" -> "User not found"
                "ERROR_WRONG_PASSWORD" -> "Incorrect password"
                else -> e.message ?: "Authentication failed"
            }
        }
    }

    // Signs out the user from firebase
    override fun signOut() = auth.signOut()

    // Deletes the current user that is being used and signs them out
    override suspend fun delete() {
        if (auth.currentUser != null) {
            auth.currentUser!!.delete()
        }
    }

    /** Convert from FirebaseUser to User */
    private fun FirebaseUser?.toUser(): User? =
        this?.let {
            if (it.email == null) {
                null
            } else {
                User(
                    email = it.email!!,
                )
            }
        }
}
