package com.hcapps.xpenzave.domain.model

import io.appwrite.models.Document

data class Response<T>(
    val id: String,
    val data: T
)

fun <T> Document<T>.toModel(data: T): Response<T> {
    return Response(
        id = this.id,
        data = data
    )
}