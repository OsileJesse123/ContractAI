package com.jesse.contractai.ui.screens.forgot_password

import androidx.annotation.StringRes

data class ForgotPasswordUiState(
    val email: String = "",
    @StringRes
    val emailError: Int? = null,
    val isLoading: Boolean = false,
    // message here can either be for successful password reset or a failed password reset
    @StringRes
    val message: Int? = null
){
    fun isFormValid() =
        // So long as email is not empty and emailError is null then form is valid.
        (email.isNotBlank() && emailError == null)
}
