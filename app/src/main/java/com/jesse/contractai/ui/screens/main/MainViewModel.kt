package com.jesse.contractai.ui.screens.main

import androidx.lifecycle.ViewModel
import com.jesse.contractai.data.repository.user_authentication.UserAuthenticationRepository
import com.jesse.contractai.domain.use_case.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    userAuthenticationRepository: UserAuthenticationRepository,
    private val signOutUseCase: SignOutUseCase
): ViewModel(){

    val rememberPassword = userAuthenticationRepository.rememberPassword

    fun signOut(){
        if (!rememberPassword){
            signOutUseCase()
        }
    }
}