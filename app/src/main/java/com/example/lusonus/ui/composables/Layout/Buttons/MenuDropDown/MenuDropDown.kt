package com.example.lusonus.ui.composables.Layout.Buttons.MenuDropDown

import MinimalDropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.lusonus.data.dataclasses.MenuItem
import com.example.lusonus.navigation.LocalGlobals
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes

/*
*   Alex made this entire file
*  */

@Composable
fun MenuDropDown() {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val navController = LocalNavController.current

    val global = LocalGlobals.current
    var menuList: List<MenuItem>

    if (global.settings.profileToggle) {
        menuList =
            listOf(
                MenuItem(
                    "Settings",
                    route = Routes.Settings.route,
                    action = { navController.navigate(Routes.Settings.route) },
                ),
                MenuItem(
                    "About Us",
                    route = Routes.FAQ.route,
                    action = { navController.navigate(Routes.FAQ.route) },
                ),
            )
    } else {
        menuList =
            listOf(
                MenuItem(
                    title = "Profile",
                    route = Routes.Profile.route,
                    action = { navController.navigate(Routes.Profile.route) },
                ),
                MenuItem(
                    "Settings",
                    route = Routes.Settings.route,
                    action = { navController.navigate(Routes.Settings.route) },
                ),
                MenuItem(
                    "About Us",
                    route = Routes.FAQ.route,
                    action = { navController.navigate(Routes.FAQ.route) },
                ),
            )
    }

    MinimalDropdownMenu(
        menuList,
        expanded,
        { expanded = !it },
        Icons.Default.MoreVert,
        navController,
    )
}
