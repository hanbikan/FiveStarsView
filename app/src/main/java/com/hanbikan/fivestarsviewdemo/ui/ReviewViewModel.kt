package com.hanbikan.fivestarsviewdemo.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReviewViewModel: ViewModel() {

    var starRating = MutableStateFlow(4.0f)
}