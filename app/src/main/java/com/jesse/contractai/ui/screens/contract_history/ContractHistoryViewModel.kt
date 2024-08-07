package com.jesse.contractai.ui.screens.contract_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.contractai.domain.use_case.ContractResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ContractHistoryViewModel @Inject constructor(
    contractResultsUseCase: ContractResultsUseCase
): ViewModel() {

    val contractHistoryItems = contractResultsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )
}