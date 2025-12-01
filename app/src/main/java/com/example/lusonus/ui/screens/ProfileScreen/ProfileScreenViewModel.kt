package com.example.lusonus.ui.screens.ProfileScreen


import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lusonus.data.auth.AuthRepository
import com.example.lusonus.data.auth.ResultAuth
import com.example.lusonus.data.model.classes.Profile
import com.example.lusonus.data.model.classes.User
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileScreenViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _currentProfile = MutableStateFlow<Profile>(Profile())
    val currentProfile: StateFlow<Profile> get() =  _currentProfile.asStateFlow()


    fun setProfile(newProfile: Profile) {
//        _currentProfile.value.EditName(newProfile.name)
//        _currentProfile.value.EditDescription(newProfile.description)
//        _currentProfile.value.EditProfileImage(if(newProfile.image == Uri.EMPTY) _currentProfile.value.image else newProfile.image)

        _currentProfile.update {
            it.copy(
                name = newProfile.name,
                description = newProfile.description
            )
        }
    }

    fun setPicture(picture: Uri){
        _currentProfile.update{it.copy(image =picture)}
    }

    fun currentUser(): StateFlow<User?> {
        return authRepository.currentUser()
    }

    private val _signInResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    private val _signUpResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    private val _signOutResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    private val _deleteAccountResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)

    val signUpResult: StateFlow<ResultAuth<Boolean>?> = _signUpResult


    fun signUp(email: String, password: String) {
        _signUpResult.value = ResultAuth.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val success = authRepository.signUp(email, password)
                _signUpResult.value = ResultAuth.Success(success)
            } catch (e: FirebaseAuthException) {
                _signUpResult.value = ResultAuth.Failure(e)
            } finally {
                // Reset the others since they are no longer applicable
                _signInResult.value = ResultAuth.Inactive
                _signOutResult.value = ResultAuth.Inactive
                _deleteAccountResult.value = ResultAuth.Inactive
            }
        }
    }
    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val success = authRepository.signIn(email, password)
                _signInResult.value = ResultAuth.Success(success)
            } catch (e: FirebaseAuthException) {
                _signInResult.value = ResultAuth.Failure(e)
            } finally {
                // Reset the others since they are no longer applicable
                _signUpResult.value = ResultAuth.Inactive
                _signOutResult.value = ResultAuth.Inactive
                _deleteAccountResult.value = ResultAuth.Inactive
            }

        }
    }
    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signOut()
        }

    }
    fun delete() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.delete()
        }
    }


}