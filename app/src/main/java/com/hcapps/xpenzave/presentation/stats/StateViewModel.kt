package com.hcapps.xpenzave.presentation.stats

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StateViewModel @Inject constructor(

): ViewModel() {

    var tabState = mutableStateOf(0)

}