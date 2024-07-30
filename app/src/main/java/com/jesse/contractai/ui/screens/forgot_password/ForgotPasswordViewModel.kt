package com.jesse.contractai.ui.screens.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.contractai.R
import com.jesse.contractai.data.exceptions.AuthenticationException
import com.jesse.contractai.data.exceptions.NetworkErrorException
import com.jesse.contractai.data.repository.user_authentication.UserAuthenticationRepository
import com.jesse.contractai.domain.use_case.ValidateEmailUseCase
import com.jesse.contractai.util.resetPasswordSuccessMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val userAuthenticationRepository: UserAuthenticationRepository
): ViewModel() {

    private val _forgotPasswordUiState: MutableStateFlow<ForgotPasswordUiState> =
        MutableStateFlow(ForgotPasswordUiState())
    val forgotPasswordUiState get() = _forgotPasswordUiState.asStateFlow()

    fun validateEmail(email: String){
        // Update TextField value before validation
        _forgotPasswordUiState.update {
                forgotPasswordUiState ->
            forgotPasswordUiState.copy(email = email)
        }
        // Validate
        _forgotPasswordUiState.update {
                forgotPasswordUiState ->
            val validateEmailUseCase = validateEmailUseCase(email)
            forgotPasswordUiState.copy(emailError = validateEmailUseCase.errorMessage)
        }
    }

    fun reset(){
        viewModelScope.launch {
            if(_forgotPasswordUiState.value.isFormValid()){
                _forgotPasswordUiState.update {
                        forgotPasswordUiState ->
                    forgotPasswordUiState.copy(isLoading = true)
                }
                val result = userAuthenticationRepository.sendPasswordResetEmail(email = _forgotPasswordUiState.value.email)
                result
                .onSuccess{
                    _forgotPasswordUiState.update {
                            forgotPasswordUiState ->
                        forgotPasswordUiState.copy(
                            isLoading = false,
                            message = resetPasswordSuccessMessage
                        )
                    }
                }
                .onFailure{
                    exception ->
                    val errorMessage = when(exception){
                        is AuthenticationException.NoUserException -> R.string.reset_password_email_failed
                        is AuthenticationException.UserDisabledException -> R.string.user_disabled
                        is AuthenticationException.UserTokenExpiredException -> R.string.user_token_expired
                        is AuthenticationException.InvalidUserTokenException -> R.string.invalid_user_token
                        is NetworkErrorException -> R.string.network_error_occured
                        is Exception -> R.string.reset_password_email_failed
                        else -> null
                    }
                    _forgotPasswordUiState.update {
                            forgotPasswordUiState ->
                        forgotPasswordUiState.copy(
                            isLoading = false,
                            message = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun onErrorMessageShown(){
        _forgotPasswordUiState.update {
            forgotPasswordUiState ->
            forgotPasswordUiState.copy(message = null)
        }
    }
}