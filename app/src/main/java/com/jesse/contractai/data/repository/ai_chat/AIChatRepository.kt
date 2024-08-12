package com.jesse.contractai.data.repository.ai_chat

import com.google.ai.client.generativeai.type.Content
import com.jesse.contractai.ui.screens.conversation.ConversationMessage
import kotlinx.coroutines.flow.Flow

interface AIChatRepository{

    fun getConversationWithAI(reviewedContractTimeStamp: Long): Flow<List<ConversationMessage>>

    suspend fun sendMessage(message: String, reviewedContractTimeStamp: Long, messageTimeStamp: Long = 0L)

    fun resetChat()
}