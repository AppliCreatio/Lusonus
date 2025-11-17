package com.example.lusonus.ui.composables.Layout.SearchAndSort

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.lusonus.data.model.MenuItem
import com.example.lusonus.ui.composables.Layout.Buttons.MenuDropDown.MinimalDropdownMenu

@Composable
fun SearchAndSort(sortOptions: List<MenuItem>, expanded: Boolean, expandFunc: (Boolean) -> Unit, searchInfo: String, searchFun: (String) -> Unit){

    Row(verticalAlignment = Alignment.CenterVertically) {
        MinimalDropdownMenu(sortOptions, expanded, expandFunc, Icons.Sharp.Menu)
        SearchBar(searchInfo, searchFun)
    }
}