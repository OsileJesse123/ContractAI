package com.jesse.contractai.ui.screens.sign_up

import androidx.annotation.StringRes

data class SignUpUiState(
    val firstName: String = "",
    @StringRes
    val firstNameError: Int? = null,
    val lastName: String = "",
    @StringRes
    val lastNameError: Int? = null,
    val email: String = "",
    @StringRes
    val emailError: Int? = null,
    val password: String = "",
    val passwordStrength: PasswordStrength = PasswordStrength.NONE,
    val confirmPassword: String = "",
    @StringRes
    val confirmPasswordError: Int? = null,
    val termsAndConditionsChecked: Boolean = false,
    @StringRes
    val errorMessage: Int? = null,
    val isLoading: Boolean = false,
    val navigateToNextScreen: Boolean = false,
    val isPasswordVisible: Boolean = true,
    val isConfirmPasswordVisible: Boolean = true
){
    fun isFormValid(): Boolean =
        (
            firstName.isNotBlank() && firstNameError == null &&
            lastName.isNotBlank() && lastNameError == null &&
            email.isNotBlank() && emailError == null &&
            password.isNotBlank() && (passwordStrength == PasswordStrength.STRONG || passwordStrength == PasswordStrength.VERY_STRONG) &&
            confirmPassword.isNotBlank() && confirmPasswordError == null && termsAndConditionsChecked &&
            confirmPassword == password
         )
}
