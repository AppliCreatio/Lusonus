package com.example.lusonus.ui.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.documentfile.provider.DocumentFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
*   Coded by Brandon
*  */

// Some research here...
// Context.functionName is apparently the nice Kotlin way to create utils functions.
// It adds it to the Context class, which makes it more accessible.
fun Context.getFileName(uri: Uri): String {
    var name = "Unknown" // default for troubleshooting

    // More research here :D
    // This is android studio's way to deal with Uri data.
    // The nulls are to get all columns, and to not have filters.
    // ".use" closes the Cursor automatically after we are done.
    // Kind of like use StreamWriter from c#.
    contentResolver.query(uri, null, null, null, null)?.use {
        // The cursor is "like" a database table where each column has an index.
            cursor ->

        // "OpenableColumns.DISPLAY_NAME" is how you get the filename of a file in android studio.
        // We use that with ".getColumnIndex" so we know what column to get the info from,
        // in this case the filename column.
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)

        // The cursor can't move by itself, so we need to tell it to move the "pointer"
        // to the first row (even though files are only 1 row).
        // I'm checking to make sure nameIndex >= 0 to ensure the column exists.
        if (cursor.moveToFirst() && nameIndex >= 0) {
            // This gets the filename from the column.
            name = cursor.getString(nameIndex)
        }
    }

    return name
}

// Gets the name by its URI. (if is path add .substringAfterLast("/") to end of it)
fun Context.getName(uri: Uri): String = uri.lastPathSegment?.substringAfterLast(":") ?: "Default"

// This is a function that deals with the coroutine responsible for the recursive calls.
suspend fun Context.scanFolderRecursive(folderUri: Uri): List<Uri> =
    withContext(Dispatchers.IO) {
        scanFolder(folderUri)
    }

// This recursively scans the folder to check for updates and returns the proper list of media.
// This was a really cool way to do recursion since it avoids scope headaches, ty that one guy
// who recommended this way in kotlin :D
private fun Context.scanFolder(folderUri: Uri): List<Uri> {
    // Gets the parent path based on the folder uri. Returns empty if invalid.
    val root = DocumentFile.fromTreeUri(this, folderUri) ?: return emptyList()

    // The media that's ready to go!
    val results = mutableListOf<Uri>()

    // The recursive function that makes sure we get every folder.
    fun scan(dir: DocumentFile) {
        // For everything in the selected folder.
        for (file in dir.listFiles()) {
            // Gets the file name.
            val name = file.name ?: ""

            // Skips hidden files.
            if (name.startsWith(".")) continue

            // If it's a folder, recurse.
            if (file.isDirectory) {
                scan(file)
                continue
            }

            // If somehow you aren't a file I don't want things breaking again.
            if (!file.isFile) continue

            // Perform voodoo magic to get the file type xD
            val mime = getMimeType(file.uri)

            // If the type is that of audio or video, we are good to go.
            if (mime.startsWith("audio/") || mime.startsWith("video/")) {
                results.add(file.uri)
            }
        }
    }

    // Starts the recursive function.
    scan(root)

    // Returns the valid media.
    return results
}

// This is what you call voodoo magic,
// apparently DocumentFile.type is super unreliable and there is a big chance things break.
// Considering our entire thing relies on files, I found that this is needed.
fun Context.getMimeType(uri: Uri): String =
    contentResolver.getType(uri) ?: run {
        val name = DocumentFile.fromSingleUri(this, uri)?.name ?: ""
        val extension = name.substringAfterLast('.', "")
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: "application/octet-stream"
    }
