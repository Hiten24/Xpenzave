package com.hcapps.xpenzave.presentation.edit_budget

import com.hcapps.xpenzave.presentation.core.component.button.ButtonState
import java.time.LocalDate

data class BudgetState(
    val amount: String = "",
    val amountError: String? = null,
    val date: LocalDate? = null,
    val buttonState: ButtonState = ButtonState()
)
