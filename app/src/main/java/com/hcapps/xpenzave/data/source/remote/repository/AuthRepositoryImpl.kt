package com.hcapps.xpenzave.data.source.remote.repository

import android.content.Context
import com.hcapps.xpenzave.util.Constant.APP_WRITE_ENDPOINT
import com.hcapps.xpenzave.util.Constant.APP_WRITE_PROJECT_ID
import com.hcapps.xpenzave.util.ResponseState
import dagger.hilt.android.qualifiers.ApplicationContext
import io.appwrite.Client
import io.appwrite.ID
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
            Timber.i("createAccountWithCredentials: user " + response.email + " registered successfully")
            ResponseState.Success(response)
        } catch (e: Exception) {
            Timber.i("createAccountWithCredentials: Failed to create account")
            e.printStackTrace()
            ResponseState.Error<AppUser>(e)
        }
    }
}