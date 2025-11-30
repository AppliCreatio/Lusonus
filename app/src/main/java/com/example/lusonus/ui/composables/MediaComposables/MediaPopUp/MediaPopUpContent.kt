//package com.example.lusonus.ui.composables.MediaComposables.MediaPopUp
//
//import android.graphics.Bitmap
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.lusonus.data.model.Media
//import com.example.lusonus.ui.composables.MediaComposables.MediaPopUp.stateless.MediaPopUpControls
//import com.example.lusonus.ui.composables.MediaComposables.MediaPopUp.stateless.MediaPopUpInfo
//
//@Composable
//fun MediaPopUpContent(
//    media: Media,
//    bitmapImage: Bitmap?,
//    isPlaying: Boolean = false,
//    onClickPopUp: (String) -> Unit,
//    onPause: () -> Unit,
//    onResume: () -> Unit,
//    onNext: () -> Unit,
//    onPrevious: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable(onClick = { onClickPopUp(media.name) }), // Optional: Makes the Box fill the available space
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer, // Set the background color of the card
//            contentColor = MaterialTheme.colorScheme.primary // Set the color of the content inside the card
//        ),
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(20.dp, 10.dp)
//                .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//
//            MediaPopUpInfo(media.name, "[Insert Name]", mediaImage = bitmapImage)
//
//            Row {
//                MediaPopUpControls(
//                    isPlaying = isPlaying, onPause = onPause,
//                    onResume = onResume,
//                    onNext = onNext,
//                    onPrevious = onPrevious,
//                )
//            }
//        }
//    }
//}
