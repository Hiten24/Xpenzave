package com.hcapps.xpenzave.data.source.remote.repository.storage

import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.storage.UploadedPhoto

interface StorageRepository {

    suspend fun createFile(path: String): RequestState<UploadedPhoto>

    suspend fun getFile(fileId: String): ByteArray?

    suspend fun deleteFile(fileId: String): RequestState<Boolean>
}