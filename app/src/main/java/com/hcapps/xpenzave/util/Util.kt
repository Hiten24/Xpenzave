package com.hcapps.xpenzave.util

import android.content.Context
import android.net.Uri
import android.provider.MediaStore.MediaColumns
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import io.appwrite.extensions.gson
import java.lang.reflect.Type

fun <T> jsonToValue(json: String, typeOfT: Type): T {
    return gson.fromJson(json, typeOfT) as T
}

@Composable
fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }

fun Context.getActualPathOfImage(uri: Uri?): String {
    if (uri == null) return ""
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
