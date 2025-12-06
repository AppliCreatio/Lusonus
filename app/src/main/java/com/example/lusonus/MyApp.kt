package com.example.lusonus

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.lusonus.data.auth.AuthRepository
import com.example.lusonus.data.auth.AuthRepositoryFirebase
import com.example.lusonus.data.interfaces.ProfileRepositoryInterface
import com.example.lusonus.data.model.repositories.ProfileRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

lateinit var appContext: Application
    private set

lateinit var appModule: AppModule
    private set

@RequiresApi(Build.VERSION_CODES.O)
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
    val authRepository: AuthRepository by lazy {
        AuthRepositoryFirebase(Firebase.auth) // inject Firebase auth
    }

    val profileRepository: ProfileRepositoryInterface by lazy {
        ProfileRepository() // inject Firebase auth
    }
}
