package com.hcapps.xpenzave.presentation.expense_detail

import com.hcapps.xpenzave.domain.model.category.Category
import java.time.LocalDateTime

data class ExpenseDetailState(
    val expenseId: String? = null,
    val amount: String? = null,
    val category: Category? = null,
    val photo: ByteArray? = null,
    val photoId: String? = null,
    val more: String? = null,
    val loading: Boolean = false,
    val date: LocalDateTime? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExpenseDetailState

        if (amount != other.amount) return false
        if (category != other.category) return false
        if (photo != null) {
            if (other.photo == null) return false
            if (!photo.contentEquals(other.photo)) return false
        } else if (other.photo != null) return false
        if (more != other.more) return false

        return true
    }

    override fun hashCode(): Int {
        var result = amount.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + (photo?.contentHashCode() ?: 0)
        result = 31 * result + more.hashCode()
        return result
    }

}
