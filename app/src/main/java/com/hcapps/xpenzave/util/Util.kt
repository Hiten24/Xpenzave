package com.hcapps.xpenzave.util

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


