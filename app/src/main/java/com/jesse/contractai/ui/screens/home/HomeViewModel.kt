package com.jesse.contractai.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.contractai.ui.model.ContractHistoryType
import com.jesse.contractai.ui.model.ContractResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(
        HomeUiState(
            userName = "Iriekefe",
            userEmail = "iriekefeanthony@gmail.com",
            contractHistory = listOf(
                ContractResult(id = "dkjfd", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
                ContractResult(id = "dfjdkf", type = ContractHistoryType.CONTRACT_REVIEW, title = "Review Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
                ContractResult(id = "ckvsjdfoe", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024")
            )
        )
    )
    val homeUiState get() = _homeUiState.asStateFlow()

}