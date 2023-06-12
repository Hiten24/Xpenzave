package com.hcapps.xpenzave.data.datastore

import com.hcapps.xpenzave.domain.model.User
import kotlinx.coroutines.flow.Flow

interface DataSore {

    suspend fun saveUser(user: User)

    fun getUser(): Flow<User>

}