package com.example.lusonus.data.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList

data class ExternalStorage(
    val name: String,
    var isConnected: Boolean,
)

val ExternalStorageListSaver: Saver<SnapshotStateList<ExternalStorage>, List<Pair<String, Boolean>>> =
    Saver(
        save = { list ->
            list.map { it.name to it.isConnected }
        },
        restore = { savedList ->
            val newList = mutableStateListOf<ExternalStorage>()
            savedList.forEach { (name, isConnected) ->
                newList.add(ExternalStorage(name, isConnected))
            }
            newList
        },
    )

@Composable
fun rememberExternalStorageList(): SnapshotStateList<ExternalStorage> =
    rememberSaveable(saver = ExternalStorageListSaver) {
        mutableStateListOf(
            ExternalStorage("test", false),
            ExternalStorage("test1", false),
            ExternalStorage("test2", false),
            ExternalStorage("test3", false),
        )
    }
