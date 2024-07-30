package com.jesse.contractai.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.contractai.data.repository.user_authentication.UserAuthenticationRepository
import com.jesse.contractai.domain.use_case.SignOutUseCase
import com.jesse.contractai.ui.model.ContractHistoryType
import com.jesse.contractai.ui.model.ContractResult
import com.jesse.contractai.ui.sign_in_handler.GoogleSignInHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userAuthenticationRepository: UserAuthenticationRepository,
    private val singOutUseCase: SignOutUseCase
): ViewModel() {

    /**
     * This is a pair of the user's first name and email
     *
     * first - user's first name
     * second - user's last name
     * */
    val userDetails: StateFlow<Pair<String, String>> = userAuthenticationRepository.user.flatMapLatest {
            user ->
        flow {
            user?.let {
                val (_, _, email, username) = it
                val firstName = username?.split(" ")?.get(0) ?: ""
                emit(Pair(firstName, email ?: ""))
            } ?: emit(Pair("", ""))
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = Pair("", "")
    )

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(
        HomeUiState(
            contractHistory = listOf(
                ContractResult(id = "dkjfd", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
                ContractResult(id = "dfjdkf", type = ContractHistoryType.CONTRACT_REVIEW, title = "Review Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
                ContractResult(id = "ckvsjdfoe", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024")
            )
        )
    )
    val homeUiState get() = _homeUiState.asStateFlow()

    fun signOut(){
        singOutUseCase()
    }
}