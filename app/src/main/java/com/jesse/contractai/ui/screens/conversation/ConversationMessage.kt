package com.jesse.contractai.ui.screens.conversation

import com.jesse.contractai.util.ConversationMessageStatus

data class ConversationMessage(
    val timeStamp: Long,
    val content: String,
    val senderType: SenderType,
    val conversationMessageStatus: ConversationMessageStatus,
    val shouldAnimate: Boolean = false
)
