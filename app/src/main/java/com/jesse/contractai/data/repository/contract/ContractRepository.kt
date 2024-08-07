package com.jesse.contractai.data.repository.contract

import com.jesse.contractai.ui.model.GeneratedContract
import kotlinx.coroutines.flow.Flow

interface ContractRepository {

    suspend fun getMoreDetailsOnContract(contractType: String, contractNature: String): Result<List<String>>

    suspend fun generateContract(contractType: String, questionsAndAnswers: List<Pair<String, String>>): Result<GeneratedContract>

    suspend fun getGeneratedContract(timeStamp: Long): Result<GeneratedContract>

    fun getAllGeneratedContracts(): Flow<List<GeneratedContract>>

    suspend fun regenerateAnotherContract(complaints: String, generatedContract: GeneratedContract): Result<GeneratedContract>
}