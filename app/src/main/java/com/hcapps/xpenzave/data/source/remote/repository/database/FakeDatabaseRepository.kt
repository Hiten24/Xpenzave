package com.hcapps.xpenzave.data.source.remote.repository.database

import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.Response
import com.hcapps.xpenzave.domain.model.budget.BudgetData
import com.hcapps.xpenzave.domain.model.budget.fakeBudgetData
import com.hcapps.xpenzave.domain.model.expense.ExpenseData
import com.hcapps.xpenzave.domain.model.expense.fakeExpenses
import com.hcapps.xpenzave.domain.model.expense.toExpenseDomainData
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class FakeDatabaseRepository @Inject constructor() : DatabaseRepository {

    override suspend fun getCategories(): CategoryResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getExpensesByMonth(date: LocalDate, filter: List<String>): ExpensesResponse {
        return try {
            delay(1000L)
            val response = fakeExpenses(10).map { Response(id = UUID.randomUUID().toString(), it) }.map { it.toExpenseDomainData() }
            val withFilter = if (filter.isNotEmpty()) response.filter { filter.contains(it.category) } else response
            RequestState.Success(withFilter)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun addExpense(expense: ExpenseData): ExpenseResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getExpense(id: String): RequestState<Response<ExpenseData>> {
        TODO("Not yet implemented")
    }

    override suspend fun createBudget(budget: BudgetData): CreateBudgetResponse {
        return try {
            delay(3000L)
            val response = BudgetData(budget.month, budget.year, budget.amount)
            RequestState.Success(Response(UUID.randomUUID().toString(), response))
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun updateBudget(id: String, budget: BudgetData): CreateBudgetResponse {
        return try {
            delay(3000L)
            val response = BudgetData(budget.month, budget.year, budget.amount)
            RequestState.Success(Response(id, response))
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun getBudgetByDate(date: LocalDate): RequestState<Response<BudgetData>> {
        return try {
            delay(1000L)
            val toModel = Response(id = UUID.randomUUID().toString(), fakeBudgetData(date.monthValue, date.year))
            RequestState.Success(toModel)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun removeExpense(id: String): RequestState<Boolean> {
        return try {
            delay(1000L)
            RequestState.Success(true)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }
}