package com.hcapps.xpenzave.data.source.remote.repository.database

import com.hcapps.xpenzave.domain.model.CategoryDataResponse
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.Response
import com.hcapps.xpenzave.domain.model.budget.BudgetData
import com.hcapps.xpenzave.domain.model.expense.ExpenseData
import com.hcapps.xpenzave.domain.model.expense.ExpenseDomainData
import java.time.LocalDate

typealias CategoryResponse = RequestState<List<Response<CategoryDataResponse>>>
typealias ExpensesResponse = RequestState<List<ExpenseDomainData>>
typealias ExpenseResponse = RequestState<Response<ExpenseData>>
typealias CreateBudgetResponse = RequestState<Response<BudgetData>>

interface DatabaseRepository {

    suspend fun getCategories(): CategoryResponse

    suspend fun getExpensesByMonth(date: LocalDate): ExpensesResponse

    suspend fun addExpense(expense: ExpenseData): ExpenseResponse

    suspend fun getExpense(id: String): RequestState<Response<ExpenseData>>

    suspend fun createBudget(budget: BudgetData): CreateBudgetResponse

    suspend fun updateBudget(id: String, budget: BudgetData): CreateBudgetResponse

    suspend fun getBudgetByDate(date: LocalDate): RequestState<Response<BudgetData>?>

}