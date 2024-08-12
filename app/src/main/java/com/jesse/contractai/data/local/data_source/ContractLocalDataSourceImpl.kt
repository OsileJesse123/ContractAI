package com.jesse.contractai.data.local.data_source

import com.jesse.contractai.data.local.database.dao.ConversationItemDao
import com.jesse.contractai.data.local.database.dao.GeneratedContractDao
import com.jesse.contractai.data.local.database.dao.ReviewedContractDao
import com.jesse.contractai.data.local.database.model.ConversationItemEntity
import com.jesse.contractai.data.local.database.model.GeneratedContractEntity
import com.jesse.contractai.data.local.database.model.ReviewedContractEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContractLocalDataSourceImpl @Inject constructor(
    private val generatedContractDao: GeneratedContractDao,
    private val reviewedContractDao: ReviewedContractDao,
    private val conversationItemDao: ConversationItemDao
): ContractLocalDataSource{

    override suspend fun insertGeneratedContract(generatedContract: GeneratedContractEntity) {
        generatedContractDao.insertGeneratedContract(generatedContract)
    }

    override suspend fun updateGeneratedContract(generatedContract: GeneratedContractEntity) {
        generatedContractDao.updateGeneratedContract(generatedContract)
    }

    override suspend fun getGeneratedContract(timeStamp: Long): GeneratedContractEntity {
        return generatedContractDao.getGeneratedContract(timeStamp)
    }

    override fun getAllGeneratedContract(userId: String): Flow<List<GeneratedContractEntity>> {
        return generatedContractDao.getAllGeneratedContract(userId)
    }

    override suspend fun deleteGeneratedContract(timeStamp: Long) {
        generatedContractDao.deleteGeneratedContract(timeStamp)
    }

    override suspend fun insertReviewedContract(reviewedContract: ReviewedContractEntity) {
        reviewedContractDao.insertReviewedContract(reviewedContract)
    }

    override suspend fun getReviewedContract(timeStamp: Long): ReviewedContractEntity {
        return reviewedContractDao.getReviewedContract(timeStamp)
    }

    override fun getAllReviewedContracts(userId: String): Flow<List<ReviewedContractEntity>> {
        return reviewedContractDao.getAllReviewedContracts(userId)
    }

    override suspend fun insertConversationItem(conversationItem: ConversationItemEntity) {
        conversationItemDao.insertConversationItem(conversationItem)
    }

    override suspend fun updateConversationItem(conversationItem: ConversationItemEntity) {
        conversationItemDao.updateConversationItem(conversationItem)
    }

    override fun getAllConversationItems(reviewedContractTimeStamp: Long): Flow<List<ConversationItemEntity>> {
        return conversationItemDao.getAllConversationItems(reviewedContractTimeStamp)
    }
}