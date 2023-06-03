package com.hcapps.xpenzave.data.source.remote.repository

import android.content.Context
import androidx.activity.ComponentActivity
import com.hcapps.xpenzave.util.Constant.APP_WRITE_ENDPOINT
import com.hcapps.xpenzave.util.Constant.APP_WRITE_PROJECT_ID
import com.hcapps.xpenzave.util.ResponseState
import dagger.hilt.android.qualifiers.ApplicationContext
import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.models.Session
import io.appwrite.services.Account
import timber.log.Timber
import javax.inject.Inject

typealias AppUser = io.appwrite.models.Account<Any>

class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
): AuthRepository {

    private lateinit var client: Client
    private lateinit var account: Account

    init {
        configureAppWrite()
    }

    private fun configureAppWrite() {
        client = Client(context)
            .setEndpoint(APP_WRITE_ENDPOINT)
            .setProject(APP_WRITE_PROJECT_ID)
            .setSelfSigned(true)
        account = Account(client)
    }

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

    override suspend fun authWithGoogle(activity: ComponentActivity): ResponseState<Boolean> {
        return try {
            account.createOAuth2Session(activity = activity, provider = "google")
            ResponseState.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseState.Error(e)
        }
    }
}