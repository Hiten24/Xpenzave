package com.hcapps.xpenzave.data.source.remote.repository

import com.hcapps.xpenzave.util.ResponseState

interface AuthRepository {

    suspend fun createAccountWithCredentials(
        email: String,
        password: String
    ): ResponseState<io.appwrite.models.Account<Any>>

}