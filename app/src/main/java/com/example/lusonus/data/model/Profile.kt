package com.example.lusonus.data.model

import android.net.Uri

class Profile(var name: String, var description: String, var profileImage: Uri) {
    fun EditName(newName: String){
        name = newName
    }

    fun EditDescription(newDescription: String){
        description = newDescription
    }

    fun EditProfileImage(newUri: Uri){
        profileImage = newUri
    }
}