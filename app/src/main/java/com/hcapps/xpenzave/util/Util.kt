package com.hcapps.xpenzave.util

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import io.appwrite.extensions.gson
import java.lang.reflect.Type
import java.time.LocalDate

fun <T> jsonToValue(json: String, typeOfT: Type): T {
    return gson.fromJson(json, typeOfT) as T
}

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

@Composable
fun rememberLocalDate() = remember { mutableStateOf(LocalDate.now()) }