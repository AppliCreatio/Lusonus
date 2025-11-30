//package com.example.lusonus.ui.composables.MediaComposables.MediaPopUp.stateless
//
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import com.example.lusonus.R
//
//@Composable
//fun MediaPopUpControls(
//    isPlaying: Boolean,
//    onPause: () -> Unit,
//    onResume: () -> Unit,
//    onNext: () -> Unit,
//    onPrevious: () -> Unit)
//{
//    IconButton(
//        onClick = onPrevious,
//        modifier = Modifier) {
//        Icon(
//            modifier = Modifier.size(30.dp),
//            painter = painterResource(R.drawable.previous_button),
//            contentDescription = "A button to skip to the previous song."
//        )
//    }
//
//    IconButton(
//        modifier = Modifier,
//        onClick = {
//            if(isPlaying)
//                onPause()
//            else onResume()
//
//
//        }
//    ) {
//        Icon(
//            modifier = Modifier.size(30.dp),
//            painter = if (isPlaying) painterResource(R.drawable.pause_button) else painterResource(R.drawable.play_button),
//            contentDescription = "A button to play and pause the current song."
//        )
//    }
//
//    IconButton(
//        onClick = onNext,
//        modifier = Modifier) {
//        Icon(
//            modifier = Modifier.size(30.dp),
//            painter = painterResource(R.drawable.next_button),
//            contentDescription = "A button to skip to the next song.",
//        )
//    }
//}