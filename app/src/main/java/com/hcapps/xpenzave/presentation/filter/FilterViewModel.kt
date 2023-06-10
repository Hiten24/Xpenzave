package com.hcapps.xpenzave.presentation.filter

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hcapps.xpenzave.presentation.add_expense.Category
import com.hcapps.xpenzave.presentation.add_expense.Category.Companion.dummies
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(

): ViewModel() {

    var amount = mutableStateOf(800)

    var categories =  mutableStateListOf(*dummies().toTypedArray())
    var selectedCategory = mutableStateListOf<Category>()
}