package com.example.lusonus.ui.screens.ProfileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lusonus.appModule

/*
*   Alex made this file
*  */

/* ViewModel Factory that will create our view model by injecting the
      authRepository from the module.
 */
class ProfileViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ProfileScreenViewModel(
            authRepository = appModule.authRepository,
            profileRepository = appModule.profileRepository
        ) as T
}
