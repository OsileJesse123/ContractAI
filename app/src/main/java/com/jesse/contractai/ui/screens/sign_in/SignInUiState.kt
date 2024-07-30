package com.jesse.contractai.ui.screens.sign_in

import androidx.annotation.StringRes

data class SignInUiState(
    val email: String = "",
    @StringRes
    val emailError: Int? = null,
    val password: String = "",
    @StringRes
    val passwordError: Int? = null,
    val isLoading: Boolean = false,
    val rememberPassword: Boolean = false,
    @StringRes
    val errorMessage: Int? = null,
    val nextDestination: NextDestination? = null,
    val isPasswordVisible: Boolean = true
){
    fun isFormValid(): Boolean =
    // So long as email and password are not empty and emailError and passwordError are null then,
        // form is valid.
        (email.isNotBlank() && password.isNotBlank() && emailError == null && passwordError == null)
}
