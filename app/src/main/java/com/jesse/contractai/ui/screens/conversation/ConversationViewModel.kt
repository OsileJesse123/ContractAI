package com.jesse.contractai.ui.screens.conversation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.contractai.data.repository.ai_chat.AIChatRepository
import com.jesse.contractai.data.repository.contract.ContractRepository
import com.jesse.contractai.navigation.ConversationDestination
import com.jesse.contractai.ui.model.ReviewedContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    private val aiChatRepository: AIChatRepository,
    private val contractRepository: ContractRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val timeStamp = savedStateHandle.get<Long>(ConversationDestination.reviewedContractTimeStampArg) ?: 0

    private val _resetScroll: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val resetScroll get() = _resetScroll.asStateFlow()

    val conversationMessages: StateFlow<List<ConversationMessage>> = aiChatRepository.getConversationWithAI(timeStamp).stateIn(
        viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    val reviewedContract: StateFlow<ReviewedContract?> =  flow{
        emit(contractRepository.getReviewContract(timeStamp).getOrNull())
    }.stateIn(
        viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )


    fun sendMessage(message: String, reviewedContractTimeStamp: Long, timeStamp: Long = 0){
        viewModelScope.launch {
            aiChatRepository.sendMessage(message, reviewedContractTimeStamp, timeStamp)
        }
    }


}