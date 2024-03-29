package com.hcapps.xpenzave.domain.model.expense

import android.os.Parcelable
import com.hcapps.xpenzave.data.local_source.entities.ExpenseEntity
import com.hcapps.xpenzave.domain.model.category.Category
import com.hcapps.xpenzave.presentation.expense_detail.ExpenseDetailNavArgs
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import kotlin.random.Random

@Parcelize
data class ExpenseDomainData(
    val id: String,
    val category: String,
    val date: LocalDateTime,
    val amount: Double,
    val photoId: String?,
    val moreDetail: String
): Parcelable {

    fun toExpenseDetailsArgs() = ExpenseDetailNavArgs(
        amount = amount.toString(),
        categoryId = category,
        photoId = photoId,
        moreDetails = moreDetail,
        date = date.toString(),
        id = id
    )

    fun toExpenseEntity() = ExpenseEntity(
        id = id,
        amount = amount,
        details = moreDetail,
        category = category,
        addThisExpenseToEachMonth = false,
        photo = photoId,
        day = date.dayOfMonth,
        month = date.monthValue,
        year = date.year,
        date = date
    )

    companion object {

        fun dummy(date: LocalDateTime = LocalDateTime.now()) = ExpenseDomainData(
            id = UUID.randomUUID().toString(),
            category = Category.dummies().random().id,
            date = date,
            amount = Random.nextDouble(100.0, 5000.0),
            photoId = null,
            moreDetail = "Lets test this",
        )
    }
}

fun getDummyExpenseByDate(date: LocalDate): Map<LocalDate, List<ExpenseDomainData>> {
    return if (date.monthValue == 5 && date.year == 2023) {
        mayDummyExpense
    } else if (date.monthValue == 6 && date.year == 2023) {
        juneDummyExpense
    } else if (date.monthValue == 4 && date.year == 2022) {
        lastYearExpense
    } else {
        emptyMap()
    }
}

private val lastYearExpense  = mapOf(
    Pair(
        LocalDate.now(),
        listOf(
            ExpenseDomainData.dummy(LocalDateTime.of(2022, 4, 30, 12, 0)),
            ExpenseDomainData.dummy(LocalDateTime.of(2022, 4, 27, 12, 0)),
            ExpenseDomainData.dummy(LocalDateTime.of(2022, 4, 25, 12, 0)),
        )
    )
)

private val juneDummyExpense = mapOf(
    Pair(LocalDate.now(), listOf(ExpenseDomainData.dummy()))
)

private val mayDummyExpense = mapOf(
    Pair(
        LocalDate.now(),
        listOf(
            ExpenseDomainData.dummy(LocalDateTime.of(2023, 5, 1, 12, 0)),
            ExpenseDomainData.dummy(LocalDateTime.of(2023, 5, 1, 12, 0)),
            ExpenseDomainData.dummy(LocalDateTime.of(2023, 5, 1, 12, 0)),
            ExpenseDomainData.dummy(LocalDateTime.of(2023, 5, 1, 12, 0)),
            ExpenseDomainData.dummy(LocalDateTime.of(2023, 5, 2, 12, 0)),
            ExpenseDomainData.dummy(LocalDateTime.of(2023, 5, 3, 12, 0)),
            ExpenseDomainData.dummy(LocalDateTime.of(2023, 5, 4, 12, 0)),
            ExpenseDomainData.dummy(LocalDateTime.of(2023, 5, 4, 12, 0)),
        )
    ),
    Pair(
        LocalDate.of(2023, 5, 2),
        listOf(ExpenseDomainData.dummy(LocalDateTime.of(2023, 5, 2, 12, 0)),)
    ),
    Pair(
        LocalDate.of(2023, 5, 3),
        listOf(ExpenseDomainData.dummy(LocalDateTime.of(2023, 5, 3, 12, 0)),)
    ),
    Pair(
        LocalDate.of(2023, 5, 4),
        listOf(
            ExpenseDomainData.dummy(LocalDateTime.of(2023, 5, 4, 12, 0)),
            ExpenseDomainData.dummy(LocalDateTime.of(2023, 5, 4, 12, 0)),
        )
    )
)