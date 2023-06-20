package com.hcapps.xpenzave.domain.usecase.auth

import com.hcapps.xpenzave.data.remote_source.repository.auth.AuthRepository
import com.hcapps.xpenzave.domain.model.RequestState
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(oldPassword: String, newPassword: String): Boolean {
        return when (val response = authRepository.changePassword(oldPassword, newPassword)) {
            is RequestState.Success -> {
                return response.data
            }
            is RequestState.Error -> {
                throw response.error
            }
            else -> false
        }
    }
}