package com.hcapps.xpenzave.data.source.remote.repository.database

import com.hcapps.xpenzave.domain.model.CategoryDataResponse
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.toModel
import com.hcapps.xpenzave.util.Constant.APP_WRITE_CATEGORY_COLLECTION_ID
import com.hcapps.xpenzave.util.Constant.APP_WRITE_DATABASE_ID
import io.appwrite.services.Databases
import timber.log.Timber
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val database: Databases
): DatabaseRepository {

    private val categoryCollection = APP_WRITE_CATEGORY_COLLECTION_ID
    private val dbId = APP_WRITE_DATABASE_ID

    override suspend fun getCategories(): CategoryResponse {
        return try {
            val response = database
                .listDocuments(
                    dbId,
                    categoryCollection,
                    nestedType = CategoryDataResponse::class.java
                )
            val mappedResponse = response.documents.map { it.toModel(it.data) }
            RequestState.Success(mappedResponse)
        } catch (e: Exception) {
            Timber.e(e)
            RequestState.Error(e)
        }
    }
}