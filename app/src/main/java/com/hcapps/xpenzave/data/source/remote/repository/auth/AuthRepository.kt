package com.hcapps.xpenzave.data.source.remote.repository.auth

import androidx.activity.ComponentActivity
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.util.ResponseState
import io.appwrite.models.Session

interface AuthRepository {

    suspend fun createAccountWithCredentials(
        email: String,
        password: String
    ): ResponseState<AppUser>

    suspend fun loginWithCredentials(
        email: String,
        password: String
    ): ResponseState<Session>

    suspend fun authenticateWithOauth2(activity: ComponentActivity, provider: String): ResponseState<Boolean>

    suspend fun logOut(): ResponseState<Boolean>

    suspend fun getAccount(): RequestState<AppUser>

}