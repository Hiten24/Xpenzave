package com.hcapps.xpenzave.util

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

@Composable
fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }

fun ContentResolver.getFileName(uri: Uri): String {
    var name = ""
    query(uri, null, null, null, null)?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        it.moveToFirst()
        name = it.getString(nameIndex)
    }
    return name
}

fun serverDateToLocalDateTime(serverDate: String) = serverDate.takeWhile { it != '+' }