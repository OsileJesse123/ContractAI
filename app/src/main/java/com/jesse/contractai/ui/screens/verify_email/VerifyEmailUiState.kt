package com.jesse.contractai.ui.screens.verify_email

import androidx.annotation.StringRes

data class VerifyEmailUiState(
    val userName: String = "",
    val isLoading: Boolean = false,
    @StringRes
    val errorMessage: Int? = null,
    val navigateToNextScreen: Boolean = false
)