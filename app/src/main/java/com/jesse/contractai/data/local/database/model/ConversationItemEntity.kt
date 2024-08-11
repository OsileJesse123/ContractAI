package com.jesse.contractai.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jesse.contractai.ui.screens.conversation.ConversationMessage
import com.jesse.contractai.util.ConversationMessageStatus
import com.jesse.contractai.ui.screens.conversation.SenderType

@Entity(tableName = "conversation_item")
data class ConversationItemEntity(
    @PrimaryKey
    val timeStamp: Long,
    @ColumnInfo(name = "reviewed_contract_time_stamp")
    val reviewedContractTimeStamp: Long,
    /**
     * This determines if this conversation item is from the user or generative ai model.
     * When value is 1 sender is ai model, if it's 2 sender is user.
     * */
    val senderType: Int,
    val content: String,
    /**
     * This determines if the message was successfully sent, failed to send or is still in the process
     * of being sent.
     *
     * 1 - is in the process of being sent.
     *
     * 2 - successfully sent.
     *
     * 3 - failed to send.
     * */
    val conversationMessageStatus: Int
){
    fun toConversationMessage(userName: String, shouldAnimate: Boolean) = ConversationMessage(
        timeStamp = timeStamp,
        content = content,
        senderType = if(senderType == 1) SenderType.AIModel() else SenderType.User(userName = userName),
        conversationMessageStatus = when(conversationMessageStatus){
            1 -> ConversationMessageStatus.SENDING
            2 -> ConversationMessageStatus.SENT
            3 -> ConversationMessageStatus.SEND_FAILED
            else ->   ConversationMessageStatus.SEND_FAILED
        },
        shouldAnimate = shouldAnimate
    )
}
