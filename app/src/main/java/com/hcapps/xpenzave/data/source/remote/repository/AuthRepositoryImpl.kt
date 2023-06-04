package com.hcapps.xpenzave.data.source.remote.repository

import androidx.activity.ComponentActivity
import com.hcapps.xpenzave.util.ResponseState
import io.appwrite.ID
import io.appwrite.models.Session
import io.appwrite.services.Account
import timber.log.Timber
import javax.inject.Inject

typealias AppUser = io.appwrite.models.Account<Any>

class AuthRepositoryImpl @Inject constructor(
    private val account: Account
): AuthRepository {

    override suspend fun createAccountWithCredentials(
        email: String,
        password: String
    ): ResponseState<AppUser> {
        return try {
            val response = account.create(
                userId = ID.unique(),
                email = email,
                password = password
            )
            Timber.i("response: $response")
            Timber.i("response: ${response.registration}")
            Timber.i("createAccountWithCredentials: user " + response.email + " registered successfully")
            ResponseState.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            if (e.message == "A user with the same email already exists in your project.") {
                ResponseState.Error(Exception("A user with the same email already exists."))
            } else {
                ResponseState.Error(e)
            }
        }
    }

    override suspend fun loginWithCredentials(
        email: String,
        password: String
    ): ResponseState<Session> {
        return try {
            val response = account.createEmailSession(
                email = email,
                password = password
            )
            ResponseState.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseState.Error(e)
        }
    }

    override suspend fun authenticateWithOauth2(
        activity: ComponentActivity,
        provider: String
    ): ResponseState<Boolean> {
        return try {
            account.createOAuth2Session(
                activity = activity,
                provider = provider
            )
            ResponseState.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseState.Error(e)
        }
    }

    override suspend fun logOut(): ResponseState<Boolean> {
        return try {
            // logs out from all the devices
            account.deleteSessions()
            ResponseState.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseState.Error(e)
        }
    }
}