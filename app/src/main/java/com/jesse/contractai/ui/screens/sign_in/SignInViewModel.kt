package com.jesse.contractai.ui.screens.sign_in

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.contractai.R
import com.jesse.contractai.data.exceptions.AuthenticationException
import com.jesse.contractai.data.exceptions.NetworkErrorException
import com.jesse.contractai.data.repository.user_authentication.UserAuthenticationRepository
import com.jesse.contractai.domain.use_case.ValidateEmailUseCase
import com.jesse.contractai.domain.use_case.ValidatePasswordUseCase
import com.jesse.contractai.ui.sign_in_handler.GoogleSignInHandler
import com.jesse.contractai.util.SignInMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val userAuthenticationRepository: UserAuthenticationRepository,
    private val googleSignInHandler: GoogleSignInHandler
): ViewModel(){

    private val _signInUiState: MutableStateFlow<SignInUiState> = MutableStateFlow(SignInUiState())
    val signInUiState get() = _signInUiState.asStateFlow()

    init {
        viewModelScope.launch {
            userAuthenticationRepository.user.collect{
                Log.e("User", "User Sign In: $it")
            }
        }
    }
    fun getSignInIntent() = googleSignInHandler.getSignInIntent()

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
            if(_signInUiState.value.isFormValid()){
                _signInUiState.update {
                        signInUiState ->
                    signInUiState.copy(isLoading = true)
                }
                val result = userAuthenticationRepository.signInWithEmailAndPassword(
                    email = _signInUiState.value.email,
                    password = _signInUiState.value.password
                )
                result
                .onSuccess {
                        user ->

                    _signInUiState.update {
                            signInUiState ->
                        signInUiState.copy(
                            nextDestination = if (user.isEmailVerified) {
                                userAuthenticationRepository.apply {
                                    rememberPassword = _signInUiState.value.rememberPassword
                                    signInMethod = SignInMethod.EMAIL_PASSWORD.name
                                }
                                NextDestination.HOME
                            } else {
                                NextDestination.VERIFY_EMAIL
                            },
                            isLoading = false
                        )
                    }
                }
                .onFailure{
                        exception ->
                    val errorMessage = when(exception){
                        is AuthenticationException.NoUserException -> R.string.user_logged_in_but_user_null
                        is AuthenticationException.InvalidCredentialsException -> R.string.invalid_credentials
                        is AuthenticationException.UserDisabledException -> R.string.user_disabled
                        is AuthenticationException.UserTokenExpiredException -> R.string.user_token_expired
                        is AuthenticationException.InvalidUserTokenException -> R.string.invalid_user_token
                        is NetworkErrorException -> R.string.network_error_occured
                        is Exception -> R.string.sign_in_failed
                        else -> null
                    }
                    _signInUiState.update {
                            signInUiState ->
                        signInUiState.copy(
                            errorMessage = errorMessage,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun signInWithGoogle(intent: Intent?){
        viewModelScope.launch {
            _signInUiState.update {
                    signInUiState ->
                signInUiState.copy(isLoading = true)
            }
            val signInToken = googleSignInHandler.getIdToken(intent)
            if (signInToken == null){
                _signInUiState.update {
                        signInUiState ->
                    signInUiState.copy(isLoading = false, errorMessage = R.string.sign_in_failed_with_google_failed)
                }
            } else{
                val result = userAuthenticationRepository.signInWithGoogle(signInToken)
                result
                .onSuccess {
                    user ->
                    _signInUiState.update {
                            signInUiState ->
                        signInUiState.copy(
                            isLoading = false,
                            nextDestination = if (user.isEmailVerified) {
                                userAuthenticationRepository.signInMethod = SignInMethod.GOOGLE.name
                                NextDestination.HOME
                            } else {
                                NextDestination.VERIFY_EMAIL
                            }
                        )
                    }
                }
                .onFailure {
                    exception ->
                    val errorMessage = when(exception){
                        is AuthenticationException.NoUserException ->  R.string.user_logged_in_but_user_null
                        is AuthenticationException.InvalidCredentialsException -> R.string.invalid_credentials
                        is AuthenticationException.UserDisabledException -> R.string.user_disabled
                        is AuthenticationException.UserTokenExpiredException -> R.string.user_token_expired
                        is AuthenticationException.InvalidUserTokenException -> R.string.invalid_user_token
                        is AuthenticationException.AuthUserCollisionException -> R.string.account_already_exist
                        is Exception -> R.string.sign_in_failed_with_google_failed
                        else -> null
                    }
                    _signInUiState.update {
                            signInUiState ->
                        signInUiState.copy(
                            errorMessage = errorMessage,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun onErrorMessageShown(){
        _signInUiState.update {
            signInUiState ->
            signInUiState.copy(errorMessage = null)
        }
    }

    fun onNavigateToNextScreen(){
        _signInUiState.update {
                signInUiState ->
            signInUiState.copy(nextDestination = null)
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

enum class NextDestination{
    VERIFY_EMAIL,
    HOME
}