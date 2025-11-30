package com.example.lusonus

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi

lateinit var appContext: Application
    private set

@RequiresApi(Build.VERSION_CODES.O)
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}
