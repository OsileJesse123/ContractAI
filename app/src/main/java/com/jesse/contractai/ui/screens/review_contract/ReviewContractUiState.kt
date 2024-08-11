package com.jesse.contractai.ui.screens.review_contract

import androidx.annotation.StringRes

data class ReviewContractUiState(
    val isLoading: Boolean = false,
    @StringRes
    val errorMessage: Int? = null,
    val navigateToNextScreen: Boolean = false
)
