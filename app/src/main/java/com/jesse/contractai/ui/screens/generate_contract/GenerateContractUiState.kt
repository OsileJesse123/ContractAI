package com.jesse.contractai.ui.screens.generate_contract

import androidx.annotation.StringRes
import com.jesse.contractai.ui.model.GeneratedContract

data class GenerateContractUiState(
    val generatedContract: GeneratedContract? = null,
    val isLoading: Boolean = false,
    @StringRes
    val errorMessage: Int? = null
)
