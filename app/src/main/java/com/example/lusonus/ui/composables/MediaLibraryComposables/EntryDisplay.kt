package com.example.lusonus.ui.composables.MediaLibraryComposables

/**
 * This composable is a stateful container that holds all the information for a single music entry
 *
 * It is stateful due to the need for dialog pop ups to see if they should stay whenever the screen rotates
 */
//@Composable
//fun EntryDisplay(musicEntry: MusicEntry) {
//
//    var deleteDialogFlag by rememberSaveable { mutableStateOf(false) }
//
//    val entryList = when (musicEntry.type) {
//        MusicEntryTypes.SONG -> LocalSongList.current
//        MusicEntryTypes.PLAYLISTS -> LocalPlaylistList.current
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxHeight()
//            .size(125.dp)
//            .background(
//                MaterialTheme.colorScheme.primary,
//                shape = RoundedCornerShape(15.dp)
//            )
//            // on click make dialog appear
//            .clickable(onClick = { deleteDialogFlag = true }),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        // Very simple image chooser library that uses Async images to fetch an image
//        // from a device asynchronously, works with uris and drawables.
//        AsyncImage(
//            model = if (musicEntry.imageUri != Uri.EMPTY) {
//                musicEntry.imageUri
//            } else R.drawable.music_default_image,
//            contentDescription = "This is the cover of the song: ${musicEntry.name} by ${musicEntry.author}.",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .clip(RoundedCornerShape(7.dp))
//                .size(90.dp)
//        )
//        Text(musicEntry.name, color = MaterialTheme.colorScheme.background)
//        Text(musicEntry.author, fontSize = 12.sp, color = MaterialTheme.colorScheme.background)
//    }
//
//    // this shows the dialog
//    if (deleteDialogFlag)
//        BasicConfirmCancelDialog(
//            onDismissRequest = { deleteDialogFlag = false },
//            onConfirmRequest = {
//                deleteDialogFlag = false
//                entryList.remove(musicEntry)
//            },
//            title = "Delete Entry",
//            description = "You're about to delete this entry. Proceed?",
//            confirmString = "Delete"
//        )
//}