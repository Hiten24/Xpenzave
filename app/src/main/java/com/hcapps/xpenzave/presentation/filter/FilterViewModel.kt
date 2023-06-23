package com.hcapps.xpenzave.presentation.filter

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hcapps.xpenzave.domain.model.category.Category.Companion.dummies
import com.hcapps.xpenzave.util.UiConstants.EXPENSE_FILTER_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.extensions.fromJson
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var categories =  mutableStateListOf(*dummies().toTypedArray())
    val selectedCategory = mutableStateListOf<String>()
    private var appliedFilter: List<String> = emptyList()

    init {
        getAppliedFilterArgs()
    }

    private fun getAppliedFilterArgs() {
        val filters = savedStateHandle.get<String>(key = EXPENSE_FILTER_ARGUMENT_KEY)?.fromJson<List<String>>() ?: emptyList()
        appliedFilter = filters
        selectedCategory.clear()
        selectedCategory.addAll(filters)
    }

    fun onRest() {
        selectedCategory.clear()
    }

    fun onSelectChange(category: String) {
        if (selectedCategory.contains(category)) {
            selectedCategory.remove(category)
        } else {
            selectedCategory.add(category)
        }
    }

}