package com.hcapps.xpenzave.data.remote_source.repository.auth

import androidx.activity.ComponentActivity
import com.hcapps.xpenzave.domain.model.RequestState
import io.appwrite.models.Session

interface AuthRepository {

    suspend fun createAccountWithCredentials(
        email: String,
        password: String
    ): RequestState<AppUser>

    suspend fun loginWithCredentials(
        email: String,
        password: String
    ): RequestState<Session>

    suspend fun authenticateWithOauth2(activity: ComponentActivity, provider: String): RequestState<Boolean>

    suspend fun logOut(): RequestState<Boolean>

    suspend fun getAccount(): RequestState<AppUser>

    suspend fun changePassword(oldPassword: String, newPassword: String): RequestState<Boolean>

    suspend fun passwordRecovery(email: String): RequestState<Boolean>

    suspend fun resetPassword(userId: String, secret: String, password: String, passwordAgain: String): RequestState<Boolean>

}