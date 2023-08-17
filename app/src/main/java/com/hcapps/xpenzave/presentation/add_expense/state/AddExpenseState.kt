package com.hcapps.xpenzave.presentation.add_expense.state

import android.net.Uri
import com.hcapps.xpenzave.domain.model.storage.UploadedPhoto
import java.time.LocalDateTime

data class AddExpenseState(
    val amount: String = "",
    val amountError: String? = null,
    val details: String = "",
    val date: LocalDateTime = LocalDateTime.now(),
    val eachMonth: Boolean = false,
    val category: String? = null,
    val photo: Uri? = null,
    val uploadedPhoto: UploadedPhoto? = null,
    val loading: Boolean = false,
    val uploadPhotoProgress: Boolean = false
)
