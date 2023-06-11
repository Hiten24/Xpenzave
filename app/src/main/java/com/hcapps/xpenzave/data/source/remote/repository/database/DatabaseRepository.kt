package com.hcapps.xpenzave.data.source.remote.repository.database

import com.hcapps.xpenzave.domain.model.CategoryDataResponse
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.Response
import com.hcapps.xpenzave.domain.model.expense.ExpenseData

typealias CategoryResponse = RequestState<List<Response<CategoryDataResponse>>>
typealias ExpenseResponse = RequestState<Response<ExpenseData>>

interface DatabaseRepository {

    suspend fun getCategories(): CategoryResponse

    suspend fun addExpense(expense: ExpenseData): ExpenseResponse

}