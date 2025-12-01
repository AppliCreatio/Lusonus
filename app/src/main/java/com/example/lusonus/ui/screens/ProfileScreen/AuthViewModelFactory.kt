package com.example.lusonus.ui.screens.ProfileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lusonus.MyApp
import com.example.lusonus.appModule
import com.example.lusonus.data.auth.AuthRepositoryFirebase

/* ViewModel Factory that will create our view model by injecting the
      authRepository from the module.
 */
class AuthViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileScreenViewModel(appModule.authRepository) as T
    }
}
