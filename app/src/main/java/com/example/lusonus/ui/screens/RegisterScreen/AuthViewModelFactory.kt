package com.example.lusonus.ui.screens.RegisterScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lusonus.appModule

/* ViewModel Factory that will create our view model by injecting the
      authRepository from the module.
 */
class AuthViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(authRepository = appModule.authRepository) as T
    }
}