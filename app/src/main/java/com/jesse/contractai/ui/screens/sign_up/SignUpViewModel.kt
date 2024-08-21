package com.jesse.contractai.ui.screens.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.contractai.R
import com.jesse.contractai.data.exceptions.AuthenticationException
import com.jesse.contractai.data.exceptions.NetworkErrorException
import com.jesse.contractai.data.repository.user_authentication.UserAuthenticationRepository
import com.jesse.contractai.domain.use_case.ValidateEmailUseCase
import com.jesse.contractai.domain.use_case.ValidateNameUseCase
import com.jesse.contractai.domain.use_case.ValidatePasswordStrengthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordStrengthUseCase: ValidatePasswordStrengthUseCase,
    private val userAuthenticationRepository: UserAuthenticationRepository
): ViewModel() {

    private val _signUpUiState: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState())
    val signUpUiState get() = _signUpUiState.asStateFlow()

    fun validateFirstName(firstName: String){
        // Update TextField value before validation
        _signUpUiState.update {
            signUpUiState ->
            signUpUiState.copy(firstName = firstName)
        }
        // Validate
        _signUpUiState.update {
                signUpUiState ->
            val validateNameUseCase = validateNameUseCase(firstName)
            signUpUiState.copy(firstNameError = validateNameUseCase.errorMessage)
        }
    }

    fun validateLastName(lastName: String){
        // Update TextField value before validation
        _signUpUiState.update {
                signUpUiState ->
            signUpUiState.copy(lastName = lastName)
        }
        // Validate
        _signUpUiState.update {
                signUpUiState ->
            val validateNameUseCase = validateNameUseCase(lastName)
            signUpUiState.copy(lastNameError = validateNameUseCase.errorMessage)
        }
    }

    fun validateEmail(email: String){
        // Update TextField value before validation
        _signUpUiState.update {
                signUpUiState ->
            signUpUiState.copy(email = email)
        }
        // Validate
        _signUpUiState.update {
                signUpUiState ->
            val validateEmailUseCase = validateEmailUseCase(email)
            signUpUiState.copy(emailError = validateEmailUseCase.errorMessage)
        }
    }

    fun validatePasswordStrength(password: String){
        // Update TextField value before validation
        _signUpUiState.update {
                signUpUiState ->
            signUpUiState.copy(password = password)
        }
        // Validate
        _signUpUiState.update {
                signUpUiState ->
            val passwordStrength = validatePasswordStrengthUseCase(password)
            signUpUiState.copy(passwordStrength = passwordStrength)
        }
    }

    fun validateConfirmPassword(confirmPassword: String){
        // Update TextField value before validation
        _signUpUiState.update {
                signUpUiState ->
            signUpUiState.copy(confirmPassword = confirmPassword)
        }
        // Validate
        _signUpUiState.update {
                signUpUiState ->
            signUpUiState.copy(confirmPasswordError = if(confirmPassword == signUpUiState.password) null else R.string.passwords_do_not_match)
        }
    }

    fun signUp(){
        viewModelScope.launch {
            if(_signUpUiState.value.isFormValid()){
                _signUpUiState.update {
                        signUpUiState ->
                    signUpUiState.copy(isLoading = true)
                }
                val result = userAuthenticationRepository.signUpWithEmailAndPassword(
                    firstName = _signUpUiState.value.firstName,
                    lastName = _signUpUiState.value.lastName,
                    email = _signUpUiState.value.email,
                    password = _signUpUiState.value.password
                )
                result
                    .onSuccess {
                        _signUpUiState.update {
                                signUpUiState ->
                            signUpUiState.copy(navigateToNextScreen = true)
                        }
                    }
                    .onFailure {
                        exception ->
                        val errorMessage = when(exception){
                            is AuthenticationException.NoUserException -> R.string.user_registered_but_user_null
                            is AuthenticationException.EmailAlreadyInUseException -> R.string.email_already_in_use
                            is AuthenticationException.AccountExistWithDifferentCredentialException -> R.string.account_already_exist
                            is AuthenticationException.CredentialAlreadyInUseException -> R.string.credential_already_in_use
                            is NetworkErrorException -> R.string.network_error_occured
                            is Exception -> R.string.sign_up_failed
                            else -> null
                        }
                        _signUpUiState.update {
                                signUpUiState ->
                            signUpUiState.copy(
                                isLoading = false,
                                errorMessage = errorMessage
                            )
                        }
                    }
            }
        }
    }

    fun toggleTermsAndConditions(termsAndConditionsChecked: Boolean){
        _signUpUiState.update {
            signUpUiState ->
            signUpUiState.copy(termsAndConditionsChecked = termsAndConditionsChecked)
        }
    }

    fun togglePasswordVisibility(isPasswordVisible: Boolean){
        _signUpUiState.update {
                signUpUiState ->
            signUpUiState.copy(isPasswordVisible = isPasswordVisible)
        }
    }

    fun toggleConfirmPasswordVisibility(isConfirmPasswordVisible: Boolean){
        _signUpUiState.update {
                signUpUiState ->
            signUpUiState.copy(isConfirmPasswordVisible = isConfirmPasswordVisible)
        }
    }

    fun onNavigateToNextScreen(){
        _signUpUiState.update {
            signUpUiState ->
            signUpUiState.copy(navigateToNextScreen = false, isLoading = false)
        }
    }

    fun onErrorMessageShown(){
        _signUpUiState.update {
                signUpUiState ->
            signUpUiState.copy(errorMessage = null)
        }
    }
}