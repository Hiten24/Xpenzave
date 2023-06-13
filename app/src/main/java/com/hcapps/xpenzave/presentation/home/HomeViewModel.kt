package com.hcapps.xpenzave.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcapps.xpenzave.data.source.remote.repository.database.DatabaseRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
): ViewModel() {

    private fun getBudget() = viewModelScope.launch {

    }

}