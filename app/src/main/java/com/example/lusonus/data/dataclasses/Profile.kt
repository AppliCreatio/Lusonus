package com.example.lusonus.data.dataclasses

import android.net.Uri

/*
*  Alex made this entire file
*  */

data class Profile(
    var name: String = "[Username]",
    var image: Uri = Uri.EMPTY,
)
