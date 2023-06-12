package com.hcapps.xpenzave.data.source.remote.repository.storage

import android.content.Context
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.storage.UploadedPhoto
import com.hcapps.xpenzave.util.Constant
import dagger.hilt.android.qualifiers.ApplicationContext
import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.models.InputFile
import io.appwrite.services.Storage
import timber.log.Timber
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
): StorageRepository {

    private lateinit var client: Client
    private lateinit var storage: Storage
    
    private val bucketId = "6485bfeea930a192884e"
    private val fileId = "6485d58322eb5f5c8f1b"

    init {
        config()
    }

    private fun config() {
        client = Client(context)
            .setEndpoint(Constant.APP_WRITE_ENDPOINT)
            .setProject(Constant.APP_WRITE_PROJECT_ID)
            .setSelfSigned(true)
        storage = Storage(client)
    }

    override suspend fun createFile(path: String): RequestState<UploadedPhoto> {
        return try {
            val file = storage.createFile(
                bucketId = bucketId,
                fileId = ID.unique(),
                file = InputFile.fromPath(path),
                onProgress = { progress ->
                    Timber.i("progress: ${progress.progress}")
                }
            )
            RequestState.Success(
                UploadedPhoto(fileId = file.id, name = file.name, bucket = file.bucketId)
            )
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun getFile(fileId: String): ByteArray? {
        return try {
            storage.getFileView(bucketId, this.fileId)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun deleteFile(fileId: String): RequestState<Boolean> {
        return try {
            storage.deleteFile(bucketId, fileId)
            RequestState.Success(true)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }
}