package com.hcapps.xpenzave.data.source.remote.repository

import com.hcapps.xpenzave.util.ResponseState
import io.appwrite.models.Session

interface AuthRepository {

    suspend fun createAccountWithCredentials(
        email: String,
        password: String
    ): ResponseState<io.appwrite.models.Account<Any>>

    suspend fun loginWithCredentials(
        email: String,
        password: String
    ): ResponseState<Session>

}