package com.hcapps.xpenzave.data.source.remote.repository.database

import com.hcapps.xpenzave.data.datastore.DataStoreService
import com.hcapps.xpenzave.data.source.remote.repository.appwrite.AppWriteUtil.permissions
import com.hcapps.xpenzave.domain.model.CategoryDataResponse
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.Response
import com.hcapps.xpenzave.domain.model.budget.BudgetData
import com.hcapps.xpenzave.domain.model.expense.ExpenseData
import com.hcapps.xpenzave.domain.model.expense.toExpenseDomainData
import com.hcapps.xpenzave.domain.model.toModel
import com.hcapps.xpenzave.util.Constant.APP_WRITE_BUDGE_COLLECTION_ID
import com.hcapps.xpenzave.util.Constant.APP_WRITE_CATEGORY_COLLECTION_ID
import com.hcapps.xpenzave.util.Constant.APP_WRITE_DATABASE_ID
import com.hcapps.xpenzave.util.Constant.APP_WRITE_EXPENSE_COLLECTION_ID
import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.services.Databases
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val database: Databases,
    dataStore: DataStoreService
): DatabaseRepository {

    private val user  = dataStore.getUserFlow()
    
    private val databaseId = APP_WRITE_DATABASE_ID
    private val categoryCollectionId = APP_WRITE_CATEGORY_COLLECTION_ID
    private val expenseCollectionId = APP_WRITE_EXPENSE_COLLECTION_ID
    private val budgetCollectionId = APP_WRITE_BUDGE_COLLECTION_ID

    override suspend fun getCategories(): CategoryResponse {
        return try {
            val response = database
                .listDocuments(
                    databaseId,
                    categoryCollectionId,
                    nestedType = CategoryDataResponse::class.java,
                )
            val mappedResponse = response.documents.map { it.toModel(it.data) }
            Timber.i(mappedResponse.toString())
            RequestState.Success(mappedResponse)
        } catch (e: Exception) {
            Timber.e(e)
            RequestState.Error(e)
        }
    }

    override suspend fun getExpensesByMonth(date: LocalDate): ExpensesResponse {
        return try {
            val response = database.listDocuments(
                databaseId = databaseId,
                collectionId = expenseCollectionId,
                nestedType = ExpenseData::class.java,
                queries = listOf(
                    Query.orderAsc("day"),
                    Query.equal("month", date.monthValue),
                    Query.equal("year", date.year)
                )
            )
            val expenses = response.documents.map { it.toModel(it.data) }.map { it.toExpenseDomainData() }
            RequestState.Success(expenses)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun addExpense(expense: ExpenseData): ExpenseResponse {
        return try {
            val response = database.createDocument(
                databaseId = databaseId,
                collectionId = expenseCollectionId,
                documentId = generateUniqueId(),
                data = expense,
                nestedType = ExpenseData::class.java,
                permissions = permissions(user.first())
            )
            val expenseModel = response.toModel(response.data)
            RequestState.Success(expenseModel)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun getExpense(id: String): RequestState<Response<ExpenseData>> {
        return try {
            val response = database.getDocument(
                databaseId = databaseId,
                collectionId = expenseCollectionId,
                documentId = id,
                nestedType = ExpenseData::class.java
            )
            RequestState.Success(response.toModel(response.data))
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun createBudget(budget: BudgetData): CreateBudgetResponse {
        return try {
            val response = database.createDocument(
                databaseId = databaseId,
                collectionId = budgetCollectionId,
                documentId = generateUniqueId(),
                data = budget,
                nestedType = BudgetData::class.java,
                permissions = permissions(user.first())
            )
            RequestState.Success(response.toModel(response.data))
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun getBudgetByDate(date: LocalDate): RequestState<Response<BudgetData>> {
        return try {
            val response = database.listDocuments(
                databaseId = databaseId,
                collectionId = budgetCollectionId,
                queries = listOf(
                    Query.equal("month", date.monthValue),
                    Query.equal("year", date.year)
                ),
                nestedType = BudgetData::class.java
            ).documents.first()
            val toModel = response.toModel(response.data)
            RequestState.Success(toModel)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    private fun generateUniqueId() = ID.unique()

}