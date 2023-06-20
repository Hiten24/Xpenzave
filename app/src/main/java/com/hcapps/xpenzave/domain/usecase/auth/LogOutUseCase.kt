package com.hcapps.xpenzave.domain.usecase.auth

import com.hcapps.xpenzave.data.datastore.DataStoreService
import com.hcapps.xpenzave.data.remote_source.repository.auth.AuthRepository
import com.hcapps.xpenzave.domain.model.RequestState
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreService: DataStoreService
) {
    suspend operator fun invoke(): Boolean {
        return when (val response = authRepository.logOut()) {
            is RequestState.Success -> {
                dataStoreService.clear()
                true
            }
            is RequestState.Error -> throw response.error
            else -> false
        }
    }
}