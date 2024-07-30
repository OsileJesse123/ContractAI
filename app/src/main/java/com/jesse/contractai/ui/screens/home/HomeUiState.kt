package com.jesse.contractai.ui.screens.home

import com.jesse.contractai.ui.model.ContractResult


data class HomeUiState(
    val contractHistory: List<ContractResult> = listOf(),
    val isLoading: Boolean = false
)
