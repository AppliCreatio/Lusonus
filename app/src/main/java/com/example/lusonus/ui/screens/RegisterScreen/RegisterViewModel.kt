package com.example.lusonus.ui.screens.RegisterScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lusonus.data.auth.AuthRepository
import com.example.lusonus.data.auth.ResultAuth
import com.example.lusonus.data.model.classes.User
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository): ViewModel() {

    fun currentUser(): StateFlow<User?> {
        return authRepository.currentUser()
    }

    private val _signInResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    private val _signUpResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    private val _signOutResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    private val _deleteAccountResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)

    val signUpResult: StateFlow<ResultAuth<Boolean>?> = _signUpResult
    val signInResult: StateFlow<ResultAuth<Boolean>?> = _signInResult


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