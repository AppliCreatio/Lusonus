package com.example.lusonus.data.model

import android.net.Uri

class Profile(var name: String = "[Username]", var description: String = "[Description]", var profileImage: Uri = Uri.EMPTY) {
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