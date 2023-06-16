package com.hcapps.xpenzave.presentation.edit_budget

import androidx.annotation.Keep
import com.hcapps.xpenzave.presentation.core.component.button.ButtonState
import java.time.LocalDate

@Keep
data class BudgetState(
    val budgetId: String? = null,
    val amount: String = "",
    val amountError: String? = null,
    val date: LocalDate? = null,
    val buttonState: ButtonState = ButtonState()
)
