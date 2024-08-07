package com.jesse.contractai.data.local.data_source

import com.jesse.contractai.data.local.database.model.GeneratedContractEntity
import kotlinx.coroutines.flow.Flow

interface ContractLocalDataSource {

    suspend fun insertGeneratedContract(generatedContract: GeneratedContractEntity)

    suspend fun updateGeneratedContract(generatedContract: GeneratedContractEntity)

    suspend fun getGeneratedContract(timeStamp: Long): GeneratedContractEntity

    suspend fun deleteGeneratedContract(timeStamp: Long)

    fun getAllGeneratedContract(userId: String): Flow<List<GeneratedContractEntity>>
}