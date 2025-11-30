package com.example.lusonus.ui.composables.Layout.BarElementSlider

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.math.abs

// I highly recommend you look at Android Studio "how to do animations in compose" or how layouts
// work if you are confused. All this I learned from their developers cite.

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BarElementSlider(
    pagerState: PagerState,
    givenSelectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = listOf("Playlists", "Media", "Folders")

    // Tracks the last page the Pager is set on.
    var lastSettledPage by remember { mutableStateOf(givenSelectedIndex) }

    // Tracks the last page we told navigation about.
    var lastNotifiedNavPage by remember { mutableStateOf(givenSelectedIndex) }

    // Flag to prevent triggering onItemSelected during programmatic scrolls
    var ignoreNextOnItemSelected by remember { mutableStateOf(false) }

    // Handle external updates (dropdown navigation etc.)
    LaunchedEffect(givenSelectedIndex) {
        if (givenSelectedIndex != pagerState.currentPage) {
            ignoreNextOnItemSelected = true
            pagerState.scrollToPage(givenSelectedIndex)
        }
        lastSettledPage = givenSelectedIndex
        lastNotifiedNavPage = givenSelectedIndex
        ignoreNextOnItemSelected = false
    }

    // Handle user scrolls
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.isScrollInProgress to pagerState.currentPage }
            .collect { (scrolling, page) ->
                if (!scrolling && page != lastSettledPage) {
                    lastSettledPage = page
                    if (!ignoreNextOnItemSelected && page != lastNotifiedNavPage) {
                        lastNotifiedNavPage = page
                        onItemSelected(page)
                    }
                }
            }
    }

    BarElementSliderStateless(
        pagerState = pagerState,
        items = items
    )
}