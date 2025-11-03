package com.example.lusonus.data.model

import android.net.Uri

data class Profile(var name: String = "[Username]", var description: String = "[Description]", var image: Uri = Uri.EMPTY)