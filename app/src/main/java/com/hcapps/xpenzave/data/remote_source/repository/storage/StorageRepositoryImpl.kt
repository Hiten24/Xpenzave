package com.hcapps.xpenzave.data.remote_source.repository.storage

import android.content.Context
import android.net.Uri
import com.hcapps.xpenzave.data.datastore.DataStoreService
import com.hcapps.xpenzave.data.remote_source.repository.appwrite.AppWriteUtil.permissions
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.storage.UploadedPhoto
import com.hcapps.xpenzave.util.Constant.APP_WRITE_EXPENSE_PHOTO_BUCKET_IT
import com.hcapps.xpenzave.util.getFileName
import dagger.hilt.android.qualifiers.ApplicationContext
import io.appwrite.ID
import io.appwrite.models.InputFile
import io.appwrite.services.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val storage: Storage,
    dataStoreService: DataStoreService
): StorageRepository {

    private val user = dataStoreService.getUserFlow()
    private val bucketId = APP_WRITE_EXPENSE_PHOTO_BUCKET_IT

    override suspend fun createFile(uri: Uri): RequestState<UploadedPhoto> {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: throw Exception("File Not Found")
            val file = File(context.cacheDir, context.contentResolver.getFileName(uri))
            withContext(Dispatchers.IO) {
                inputStream.copyTo(FileOutputStream(file))
                val uploadedFile = storage.createFile(
                    bucketId = bucketId,
                    fileId = ID.unique(),
                    file = InputFile.fromFile(file),
                    onProgress = { progress ->
                        Timber.i("progress: ${progress.progress}")
                    },
                    permissions = permissions(user.first())
                )
                inputStream.close()
                file.delete()
                RequestState.Success(
                    UploadedPhoto(
                        fileId = uploadedFile.id,
                        name = uploadedFile.name,
                        bucket = uploadedFile.bucketId
                    )
                )
            }
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