package com.example.lusonus.ui.screens.ProfileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lusonus.appModule

/* ViewModel Factory that will create our view model by injecting the
      authRepository from the module.
 */
class ProfileViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileScreenViewModel(authRepository = appModule.authRepository, profileRepository = appModule.profileRepository) as T
    }
}