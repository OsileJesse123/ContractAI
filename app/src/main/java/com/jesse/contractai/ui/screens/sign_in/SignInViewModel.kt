package com.jesse.contractai.ui.screens.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.contractai.domain.use_case.ValidateEmailUseCase
import com.jesse.contractai.domain.use_case.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
): ViewModel(){

    private val _signInUiState: MutableStateFlow<SignInUiState> = MutableStateFlow(SignInUiState())
    val signInUiState get() = _signInUiState.asStateFlow()

    fun validateEmail(email: String){
        // Update TextField value before validation
        _signInUiState.update {
            signInUiState ->
            signInUiState.copy(email = email)
        }
        // Validate
        _signInUiState.update {
            signInUiState ->
            val validateEmailUseCase = validateEmailUseCase(email)
            signInUiState.copy(emailError = validateEmailUseCase.errorMessage)
        }
    }

    fun validatePassword(password: String){
        // Update TextField value before validation
        _signInUiState.update {
                signInUiState ->
            signInUiState.copy(password = password)
        }
        // Validate
        _signInUiState.update {
                signInUiState ->
            val validatePasswordUseCase = validatePasswordUseCase(password)
            signInUiState.copy(passwordError = validatePasswordUseCase.errorMessage)
        }
    }

    fun signIn(){
        viewModelScope.launch {
            // TODO: add actual implementation
            if(_signInUiState.value.isFormValid()){
                _signInUiState.update {
                        signInUiState ->
                    signInUiState.copy(isLoading = true)
                }
                delay(2_000)
                _signInUiState.update {
                        signInUiState ->
                    signInUiState.copy(isLoading = false, navigateToNextScreen = true)
                }
            }
        }
    }

    fun signInWithGoogle(){
        viewModelScope.launch {
            // TODO: add actual implementation
            _signInUiState.update {
                    signInUiState ->
                signInUiState.copy(isLoading = true)
            }
            delay(2_000)
            _signInUiState.update {
                    signInUiState ->
                signInUiState.copy(isLoading = false)
            }
        }
    }

    fun rememberPassword(rememberPassword: Boolean){
        _signInUiState.update {
            signInUiState ->
            signInUiState.copy(rememberPassword = rememberPassword)
        }
    }

    fun togglePasswordVisibility(isPasswordVisible: Boolean){
        _signInUiState.update {
            signInUiState ->
            signInUiState.copy(isPasswordVisible = isPasswordVisible)
        }
    }
}