package com.hcapps.xpenzave.util

import android.content.Context
import android.net.Uri
import android.provider.MediaStore.MediaColumns
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import io.appwrite.extensions.gson
import java.lang.reflect.Type

fun <T> jsonToValue(json: String, classOfT: Class<T>): T {
    return gson.fromJson(json, classOfT) as T
}

fun <T> jsonToValue(json: String, typeOfT: Type): T {
    return gson.fromJson(json, typeOfT) as T
}

fun <T> valueToString(value: T): String {
    return gson.toJson(value)
}

@Composable
fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }

fun Context.getActualPathOfImage(uri: Uri): String {
    var path = ""
    contentResolver.query(
        uri,
        arrayOf(MediaColumns.DATA),
        null,
        null,
        null
    )?.use { cursor ->
        cursor.moveToFirst()
        path = cursor.getString(cursor.getColumnIndexOrThrow(MediaColumns.DATA))
    }
    return path
}
