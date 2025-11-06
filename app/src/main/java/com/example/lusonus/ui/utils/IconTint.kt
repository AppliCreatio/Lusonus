package com.example.organisemedia.Helper.Style

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

// Helper function to set tints to icon buttons
fun IconTint(
    currentRoute: String?,
    route: String,
    colorScheme: ColorScheme
): Color {
    return if (currentRoute == route)
        colorScheme.primary
    else
        colorScheme.onSurfaceVariant
}