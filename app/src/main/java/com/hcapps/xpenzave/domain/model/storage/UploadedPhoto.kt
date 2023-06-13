package com.hcapps.xpenzave.domain.model.storage

data class UploadedPhoto(
    val fileId: String,
    val name: String,
    val bucket: String
)
