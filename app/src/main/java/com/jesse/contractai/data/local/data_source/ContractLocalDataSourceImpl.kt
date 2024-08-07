package com.jesse.contractai.data.local.data_source

import com.jesse.contractai.data.local.database.dao.GeneratedContractDao
import com.jesse.contractai.data.local.database.model.GeneratedContractEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContractLocalDataSourceImpl @Inject constructor(
    private val generatedContractDao: GeneratedContractDao
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
}