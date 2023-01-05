package com.abit.fivestarsview

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel: ViewModel() {

    val starRating = MutableStateFlow(1.5f)
}