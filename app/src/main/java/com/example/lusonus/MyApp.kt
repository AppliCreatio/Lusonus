package com.example.lusonus

import android.app.Application
import android.content.Context
import com.example.lusonus.data.auth.AuthRepository
import com.example.lusonus.data.auth.AuthRepositoryFirebase
import com.example.lusonus.data.interfaces.ProfileRepositoryInterface
import com.example.lusonus.data.model.repositories.ProfileRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

/*
*   Coded by Brandon and changes made by Alex
*  */

lateinit var appContext: Application
    private set

lateinit var appModule: AppModule
    private set

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
        appModule = AppModule(appContext)
    }
}

class AppModule(
    private val appContext: Context,
) {

    // Authentication repository instance that is accessible throughout the app
    val authRepository: AuthRepository by lazy {
        AuthRepositoryFirebase(Firebase.auth) // inject Firebase auth
    }

    // Profile repository instance that is accessible throughout the app
    val profileRepository: ProfileRepositoryInterface by lazy {
        ProfileRepository()
    }
}
