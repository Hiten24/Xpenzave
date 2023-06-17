package com.hcapps.xpenzave.presentation.filter

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hcapps.xpenzave.domain.model.category.Category.Companion.dummies
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

//    var amount = mutableStateOf(800)

    var categories =  mutableStateListOf(*dummies().toTypedArray())
    val selectedCategory = mutableStateListOf<String>()
    private var appliedFilter: List<String> = emptyList()

    /*init {
        getAppliedFilterArgs()
    }*/

    /*private fun getAppliedFilterArgs() {
        val filters = savedStateHandle.get<String>(key = EXPENSE_FILTER_ARGUMENT_KEY)?.fromJson<List<String>>() ?: emptyList()
        appliedFilter = filters
        selectedCategory.clear()
        selectedCategory.addAll(filters)
        Timber.i("Stats to Filter: $filters")
    }*/

    fun getSelectedCategoriesId(): Array<String> = selectedCategory.toTypedArray()

    fun onRest() {
        selectedCategory.clear()
    }

    fun isFilterChanged() = appliedFilter.toSet() != selectedCategory.toSet()

    fun onSelectChange(category: String) {
        if (selectedCategory.contains(category)) {
            selectedCategory.remove(category)
        } else {
            selectedCategory.add(category)
        }
    }

}