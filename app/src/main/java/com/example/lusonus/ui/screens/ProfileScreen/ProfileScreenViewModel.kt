package com.example.lusonus.ui.screens.ProfileScreen

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.ass3_appdev.screens.main.ConnectedStorageSaver
import com.example.lusonus.data.model.ExternalStorage
import com.example.lusonus.data.model.Profile

class ProfileScreenViewModel : ViewModel() {

    var currentProfile = mutableStateOf<Profile>(Profile())
        private set

    fun getCurrentProfile(): Profile{
        return currentProfile.value
    }

    fun EditProfile(){

    }
}