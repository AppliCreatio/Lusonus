package com.example.lusonus.ui.composables.Layout.Buttons.MenuDropDown

import MinimalDropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.lusonus.data.model.MenuItem
import com.example.lusonus.navigation.LocalGlobals
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes

@Composable
fun MenuDropDown(){
    var expanded by rememberSaveable { mutableStateOf(false) }
    val navController = LocalNavController.current
    val settings = LocalGlobals.current.settings

    val menuList: List<MenuItem> = if (!settings.profileToggle) {
        listOf(
            MenuItem("Profile") { navController.navigate(Routes.Profile.route) },
            MenuItem("Settings", ) { navController.navigate(Routes.Settings.route) },
            MenuItem("About Us") { navController.navigate(Routes.FAQ.route) })
    }
    else {
        listOf(
            MenuItem("Settings", ) { navController.navigate(Routes.Settings.route) },
            MenuItem("About Us") { navController.navigate(Routes.FAQ.route) })
    }

    MinimalDropdownMenu(menuList, expanded, { expanded = !it }, Icons.Default.MoreVert)
}