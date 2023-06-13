package com.hcapps.xpenzave.data.source.remote.repository.storage

import com.hcapps.xpenzave.data.datastore.DataStoreService
import com.hcapps.xpenzave.data.source.remote.repository.appwrite.AppWriteUtil.permissions
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.storage.UploadedPhoto
import com.hcapps.xpenzave.util.Constant.APP_WRITE_EXPENSE_PHOTO_BUCKET_IT
import io.appwrite.ID
import io.appwrite.models.InputFile
import io.appwrite.services.Storage
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storage: Storage,
    dataStoreService: DataStoreService
): StorageRepository {

    private val user = dataStoreService.getUserFlow()
    private val bucketId = APP_WRITE_EXPENSE_PHOTO_BUCKET_IT

    override suspend fun createFile(path: String): RequestState<UploadedPhoto> {
        return try {
            val file = storage.createFile(
                bucketId = bucketId,
                fileId = ID.unique(),
                file = InputFile.fromPath(path),
                onProgress = { progress ->
                    Timber.i("progress: ${progress.progress}")
                },
                permissions = permissions(user.first())
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
            storage.getFileView(bucketId, fileId)
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