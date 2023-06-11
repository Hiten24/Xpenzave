package com.hcapps.xpenzave.data.source.remote.repository.storage

interface StorageRepository {

    suspend fun createFile(path: String)

    suspend fun getFile(fileId: String): ByteArray?

    suspend fun deleteFile(fileId: String)
}