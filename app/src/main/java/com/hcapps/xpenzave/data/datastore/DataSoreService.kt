package com.hcapps.xpenzave.data.datastore

import com.hcapps.xpenzave.domain.model.User
import kotlinx.coroutines.flow.Flow

interface DataStoreService {

    suspend fun saveUser(user: User)

    fun getUser(): User

    fun getUserFlow(): Flow<User>

}