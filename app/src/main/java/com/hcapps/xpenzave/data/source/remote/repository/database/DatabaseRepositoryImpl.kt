package com.hcapps.xpenzave.data.source.remote.repository.database

import com.hcapps.xpenzave.domain.model.CategoryDataResponse
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.expense.ExpenseData
import com.hcapps.xpenzave.domain.model.toModel
import com.hcapps.xpenzave.util.Constant.APP_WRITE_CATEGORY_COLLECTION_ID
import com.hcapps.xpenzave.util.Constant.APP_WRITE_DATABASE_ID
import com.hcapps.xpenzave.util.Constant.APP_WRITE_EXPENSE_COLLECTION_ID
import io.appwrite.ID
import io.appwrite.services.Databases
import timber.log.Timber
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val database: Databases
): DatabaseRepository {

    private val databaseId = APP_WRITE_DATABASE_ID
    private val categoryCollectionId = APP_WRITE_CATEGORY_COLLECTION_ID
    private val expenseCollectionId = APP_WRITE_EXPENSE_COLLECTION_ID

    override suspend fun getCategories(): CategoryResponse {
        return try {
            val response = database
                .listDocuments(
                    databaseId,
                    categoryCollectionId,
                    nestedType = CategoryDataResponse::class.java
                )
            val mappedResponse = response.documents.map { it.toModel(it.data) }
            Timber.i(mappedResponse.toString())
            RequestState.Success(mappedResponse)
        } catch (e: Exception) {
            Timber.e(e)
            RequestState.Error(e)
        }
    }

    override suspend fun addExpense(expense: ExpenseData): ExpenseResponse {
        return try {
            val response = database.createDocument(
                databaseId = databaseId,
                collectionId = expenseCollectionId,
                documentId = generateUniqueId(),
                data = expense.copy(),
                nestedType = ExpenseData::class.java
            )
            val expenseModel = response.toModel(response.data)
            Timber.i("response \n $expenseModel")
            RequestState.Success(expenseModel)
        } catch (e: Exception) {
            Timber.e(e)
            RequestState.Error(e)
        }
    }

    private fun generateUniqueId() = ID.unique()

}