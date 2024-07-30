package com.jesse.contractai.ui.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.contractai.data.repository.user_authentication.UserAuthenticationRepository
import com.jesse.contractai.domain.use_case.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userAuthenticationRepository: UserAuthenticationRepository,
    private val signOutUseCase: SignOutUseCase
): ViewModel(){

    val rememberPassword = userAuthenticationRepository.rememberPassword

    fun signOut(){
        if (!rememberPassword){
            Log.e("SignOutMain", "Sign out ran")
            signOutUseCase()
        }
    }
}