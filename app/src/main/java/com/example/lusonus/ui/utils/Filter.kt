package com.example.lusonus.ui.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.lusonus.data.model.Entries
import com.example.lusonus.data.model.Media
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun filter(mediaList: List<Media>, restrictionType: Int): List<Media> {
    return when(restrictionType){
        1 -> mediaList.filter { it.uri.path?.endsWith(".mp3") ?: false }
        2 -> mediaList.filter { it.uri.path?.endsWith(".mp4") ?: false }
        else -> mediaList
    }
}