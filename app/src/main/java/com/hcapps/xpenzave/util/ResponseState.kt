package com.hcapps.xpenzave.util

sealed class ResponseState<out T> {
    object Idle: ResponseState<Nothing>()
    object Loading: ResponseState<Nothing>()
    data class Success<T>(val data: T): ResponseState<T>()
    data class Error(val error: Throwable): ResponseState<Nothing>()
}