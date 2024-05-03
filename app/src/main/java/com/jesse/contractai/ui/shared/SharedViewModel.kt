package com.jesse.contractai.ui.shared

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(): ViewModel() {

    var contractType: String = ""
        set(value) {
            if (value.isNotEmpty())
                field = value
        }
    var natureOfContract: String = ""
        set(value) {
            if (value.isNotEmpty())
                field = value
        }
    var answers: List<String> = listOf()
        set(value) {
            if (value.isNotEmpty())
                field = value
        }
}