package com.example.lusonus.data.auth

import com.example.lusonus.data.model.classes.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class AuthRepositoryFirebase(private val auth: FirebaseAuth) : AuthRepository {
    private val currentUserStateFlow = MutableStateFlow(auth.currentUser?.toUser())

    init {
        auth.addAuthStateListener { firebaseAuth ->
            currentUserStateFlow.value = firebaseAuth.currentUser?.toUser()
        }
    }

    /* Returns the current user signed in */
    override fun currentUser(): StateFlow<User?> {
        return currentUserStateFlow
    }

    /* Creates an account in the firebase with the given password and email if the email does not
    * already exist within the firebase */
    override suspend fun signUp(email: String, password: String): Boolean {
        try {
            auth.createUserWithEmailAndPassword(email,password).await()
            return true
        } catch (e: Exception){
            return false
        }
    }

    /* Signs the user into firebase using the provided email and password they inputted */
    override suspend fun signIn(email: String, password: String): Boolean {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            return true
        } catch(e: Exception){
            return false
        }
    }

    /* Signs out the user from firebase */
    override fun signOut() {
        return auth.signOut()
    }

    /* Deletes the current user that is being used and signs them out */
    override suspend fun delete() {
        if(auth.currentUser != null)
            auth.currentUser!!.delete()
    }

    /** Convert from FirebaseUser to User */
    private fun FirebaseUser?.toUser(): User? {
        return this?.let {
            if (it.email == null) null else
                User(
                    email = it.email!!)
        }
    }
}

