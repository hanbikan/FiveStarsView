package com.hanbikan.fivestarsviewdemo.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReviewViewModel: ViewModel() {

    private val _average = MutableStateFlow(4.7f)
    val average: StateFlow<Float> = _average
}