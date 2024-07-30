package com.jesse.contractai.ui.screens.verify_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.contractai.R
import com.jesse.contractai.data.exceptions.AuthenticationException
import com.jesse.contractai.data.exceptions.NetworkErrorException
import com.jesse.contractai.data.repository.user_authentication.UserAuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val userAuthenticationRepository: UserAuthenticationRepository
): ViewModel(){

    val userEmail: StateFlow<String> = userAuthenticationRepository.user.flatMapLatest {
            user ->
        flow {
            user?.let {
                val (_, _, email, _) = it
                emit(email ?: "")
            } ?: emit("")
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ""
    )

    private val verifyEmailUiAction: MutableSharedFlow<VerifyEmailUiAction> = MutableSharedFlow(replay = 1)

    val verifyEmailUiState: StateFlow<VerifyEmailUiState> = verifyEmailUiAction.flatMapLatest{
            uiAction ->
        flow {
            when(uiAction){
                VerifyEmailUiAction.Send -> {
                    emit(VerifyEmailUiState(isLoading = true))
                    val result = userAuthenticationRepository.verifyUserEmail()
                    result
                        .onSuccess {
                            emit(VerifyEmailUiState(isLoading = false))
                        }
                        .onFailure {
                            exception ->
                            val errorMessage = when(exception){
                                is NetworkErrorException -> R.string.network_error_occured
                                is AuthenticationException.TooManyRequestsException -> R.string.too_many_requests
                                is Exception ->  R.string.send_email_link_failed
                                else -> null
                            }
                            emit(VerifyEmailUiState(
                                errorMessage = errorMessage
                            ))
                        }
                }
                VerifyEmailUiAction.OnErrorMessageShown -> {
                    emit(VerifyEmailUiState(errorMessage = null))
                }
                VerifyEmailUiAction.NavigateToNextScreen -> {
                    emit(VerifyEmailUiState(navigateToNextScreen = true))
                }
            }
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = VerifyEmailUiState()
    )

    init {
        sendUiAction(VerifyEmailUiAction.Send)
        hasTheUserBeenVerified()
    }

    fun sendUiAction(uiAction: VerifyEmailUiAction) {
        viewModelScope.launch {
            verifyEmailUiAction.emit(uiAction)
        }
    }

    private fun hasTheUserBeenVerified(){
        // This runs a check every 1.5 second to see if user has verified their mail and updates
        // the UI as per required.
        viewModelScope.launch {
            while (isActive){
                delay(1_500)
                val userEmailVerified = userAuthenticationRepository.hasTheUserBeenVerified()
                if (userEmailVerified){
                    verifyEmailUiAction.emit(VerifyEmailUiAction.NavigateToNextScreen)
                    cancel()
                }
            }
        }
    }
}