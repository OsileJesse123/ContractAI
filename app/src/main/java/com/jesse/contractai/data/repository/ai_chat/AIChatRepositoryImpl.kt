package com.jesse.contractai.data.repository.ai_chat

import com.google.ai.client.generativeai.type.content
import com.jesse.contractai.data.local.data_source.ContractLocalDataSource
import com.jesse.contractai.data.local.database.model.ConversationItemEntity
import com.jesse.contractai.data.network.data_source.ContractNetworkDataSource
import com.jesse.contractai.data.network.service.user_auth_service.UserAuthenticationService
import com.jesse.contractai.di.ContractAIDispatchers
import com.jesse.contractai.di.Dispatcher
import com.jesse.contractai.ui.screens.conversation.ConversationMessage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AIChatRepositoryImpl @Inject constructor(
    private val contractNetworkDataSource: ContractNetworkDataSource,
    private val contractLocalDataSource: ContractLocalDataSource,
    private val userAuthenticationService: UserAuthenticationService,
    @Dispatcher(ContractAIDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(ContractAIDispatchers.DEFAULT) private val defaultDispatcher: CoroutineDispatcher,
): AIChatRepository {

    override fun getConversationWithAI(reviewedContractTimeStamp: Long): Flow<List<ConversationMessage>> {
        return contractLocalDataSource.getAllConversationItems(reviewedContractTimeStamp).flatMapLatest {
            conversationItemEntities ->
            val user = userAuthenticationService.user.firstOrNull()
                ?: return@flatMapLatest flow<List<ConversationMessage>> { emit(
                listOf()
            ) }
            if(!contractNetworkDataSource.isChatInitialized()){
                contractNetworkDataSource.initializeChat(
                    history = conversationItemEntities.map {
                        val senderType = if(it.senderType == 1) "model" else "user"
                        content(senderType){text(it.content)}
                    }
                )
            }
            flow {
               emit( conversationItemEntities.map {
                       conversationItemEntity ->
                   val shouldAnimate = conversationItemEntities.indexOf(conversationItemEntity) == conversationItemEntities.lastIndex
                   conversationItemEntity.toConversationMessage(user.userName ?: "", shouldAnimate)
               })
            }
        }.flowOn(defaultDispatcher)
    }

    override suspend fun sendMessage(message: String, reviewedContractTimeStamp: Long, messageTimeStamp: Long){
        withContext(ioDispatcher){
            val timeStamp = if(messageTimeStamp == 0L) System.currentTimeMillis() else messageTimeStamp
            val conversationMessageItem = ConversationItemEntity(
                timeStamp = timeStamp,
                reviewedContractTimeStamp = reviewedContractTimeStamp,
                senderType = 2,
                content = message,
                conversationMessageStatus = 1
            )
            try {
                contractLocalDataSource.insertConversationItem(conversationMessageItem)
                val result = contractNetworkDataSource.sendMessage(message)
                if (result != null){
                    contractLocalDataSource.updateConversationItem(conversationMessageItem.copy(conversationMessageStatus = 2))
                    contractLocalDataSource.insertConversationItem(
                        ConversationItemEntity(
                            timeStamp = System.currentTimeMillis(),
                            reviewedContractTimeStamp = reviewedContractTimeStamp,
                            senderType = 1,
                            content = result,
                            conversationMessageStatus = 2
                        )
                    )
                } else {
                    contractLocalDataSource.updateConversationItem(conversationMessageItem.copy(conversationMessageStatus = 3))
                }
            } catch (e: Exception){
                contractLocalDataSource.updateConversationItem(conversationMessageItem.copy(conversationMessageStatus = 3))
            }
        }
    }

    override fun resetChat() {
        contractNetworkDataSource.resetChat()
    }
}