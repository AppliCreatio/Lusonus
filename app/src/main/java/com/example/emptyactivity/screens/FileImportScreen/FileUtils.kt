package com.example.filedisplayer

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

// Some research here...
// Context.functionName is apparently the nice Kotlin way to create utils functions.
// It adds it to the Context class, which makes it more accessible.
fun Context.getFileName(uri: Uri): String {
    var name = "Unknown" //default for troubleshooting

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