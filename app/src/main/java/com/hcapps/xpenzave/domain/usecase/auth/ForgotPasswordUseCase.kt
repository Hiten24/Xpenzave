package com.hcapps.xpenzave.domain.usecase.auth

import com.hcapps.xpenzave.data.remote_source.repository.auth.AuthRepository
import com.hcapps.xpenzave.domain.model.RequestState
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(email: String): Boolean {
        return when (val response = authRepository.passwordRecovery(email)) {
            is RequestState.Success -> { true }
            is RequestState.Error -> { throw response.error }
            else -> { false }
        }
    }

}