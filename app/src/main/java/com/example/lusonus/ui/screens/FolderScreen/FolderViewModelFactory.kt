package com.example.lusonus.ui.screens.FolderScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FolderViewModelFactory(
    private val folderName: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FolderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FolderViewModel(folderName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
