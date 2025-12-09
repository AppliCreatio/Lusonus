package com.example.lusonus.data.dataclasses

/**
 * Data class that is use for an option within a drop down menu. Made by alex
 */
data class MenuItem(
    val title: String,
    val action: () -> Unit,
    val route: String? = null,
)
