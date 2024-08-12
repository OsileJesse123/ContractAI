package com.jesse.contractai.data.local.data_source

import com.jesse.contractai.data.local.database.model.ConversationItemEntity
import com.jesse.contractai.data.local.database.model.GeneratedContractEntity
import com.jesse.contractai.data.local.database.model.ReviewedContractEntity
import kotlinx.coroutines.flow.Flow

interface ContractLocalDataSource {

    suspend fun insertGeneratedContract(generatedContract: GeneratedContractEntity)

    suspend fun updateGeneratedContract(generatedContract: GeneratedContractEntity)

    suspend fun getGeneratedContract(timeStamp: Long): GeneratedContractEntity

    suspend fun deleteGeneratedContract(timeStamp: Long)

    fun getAllGeneratedContract(userId: String): Flow<List<GeneratedContractEntity>>

    suspend fun insertReviewedContract(reviewedContract: ReviewedContractEntity)

    suspend fun getReviewedContract(timeStamp: Long): ReviewedContractEntity

    fun getAllReviewedContracts(userId: String): Flow<List<ReviewedContractEntity>>

    suspend fun insertConversationItem(conversationItem: ConversationItemEntity)

    suspend fun updateConversationItem(conversationItem: ConversationItemEntity)

    fun getAllConversationItems(reviewedContractTimeStamp: Long): Flow<List<ConversationItemEntity>>


}