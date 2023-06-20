package com.hcapps.xpenzave.domain.usecase.auth

import com.hcapps.xpenzave.data.datastore.DataStoreService
import com.hcapps.xpenzave.data.remote_source.repository.auth.AuthRepository
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.toUser
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreService: DataStoreService
) {
    suspend operator fun invoke(email: String, password: String) {
        when (val response = authRepository.createAccountWithCredentials(email, password)) {
            is RequestState.Success -> {
                val user = response.data.toUser()
                dataStoreService.saveUser(user)
            }
            is RequestState.Error -> throw response.error
            else -> {}
        }
    }
}