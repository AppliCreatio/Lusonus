package com.example.lusonus.ui.composables.Layout.SearchAndSort

import MinimalDropdownMenu
import SearchBar
import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.lusonus.data.dataclasses.MenuItem

@Composable
fun SearchAndSort(
    sortOptions: List<MenuItem>,
    expanded: Boolean,
    expandFunc: (Boolean) -> Unit,
    searchInfo: String,
    searchFun: (String) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (!isLandscape)
    {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MinimalDropdownMenu(
                menuItems = sortOptions,
                expanded = expanded,
                toggleAction = expandFunc,
                icon = Icons.Sharp.Menu,
            )

            Spacer(Modifier.width(12.dp))

            SearchBar(
                searchInfo = searchInfo,
                searchAction = searchFun,
                modifier = Modifier.weight(1f),
            )
        }
    }
}
