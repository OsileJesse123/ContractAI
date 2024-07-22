package com.jesse.contractai.ui.screens.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.contractai.domain.use_case.ValidateEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase
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
                delay(2_000)
                _forgotPasswordUiState.update {
                        forgotPasswordUiState ->
                    forgotPasswordUiState.copy(isLoading = false)
                }
            }
        }
    }
}