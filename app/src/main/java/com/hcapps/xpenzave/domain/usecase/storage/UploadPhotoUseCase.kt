package com.hcapps.xpenzave.domain.usecase.storage

import android.net.Uri
import com.hcapps.xpenzave.data.remote_source.repository.storage.StorageRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.storage.UploadedPhoto
import javax.inject.Inject

class UploadPhotoUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) {

    suspend operator fun invoke(uri: Uri): UploadedPhoto? {
        return when(val response = storageRepository.createFile(uri)) {
            is RequestState.Success -> response.data
            is RequestState.Error -> throw response.error
            else -> null
        }
    }

}