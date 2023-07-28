package com.hcapps.xpenzave.data.remote_source.repository.storage

import android.net.Uri
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.storage.UploadedPhoto

interface StorageRepository {

    suspend fun createFile(uri: Uri): RequestState<UploadedPhoto>

    suspend fun getFile(fileId: String): ByteArray?

    suspend fun deleteFile(fileId: String): RequestState<Boolean>
}