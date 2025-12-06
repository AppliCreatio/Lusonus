package com.example.lusonus.ui.utils

import android.annotation.SuppressLint

// Calculates the timestamp based on the provided milliseconds.
@SuppressLint("DefaultLocale")
public fun formatTimeFromMilliseconds(milliseconds: Long): String {
    // Gets the total seconds.
    val totalSeconds = (milliseconds / 1000L).toInt()

    // Gets the minutes.
    val minutes = totalSeconds / 60

    // Gets the remaining seconds.
    val seconds = totalSeconds % 60

    // Formats it as a string.
    return String.format("%d:%02d", minutes, seconds)
}
