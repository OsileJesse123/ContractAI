package com.jesse.contractai.data.repository.contract

import com.jesse.contractai.ui.model.GeneratedContract
import com.jesse.contractai.ui.model.ReviewedContract
import kotlinx.coroutines.flow.Flow

interface ContractRepository {

    suspend fun getMoreDetailsOnContract(contractType: String, contractNature: String): Result<List<String>>

    suspend fun generateContract(contractType: String, questionsAndAnswers: List<Pair<String, String>>): Result<GeneratedContract>

    suspend fun getGeneratedContract(timeStamp: Long): Result<GeneratedContract>

    fun getAllGeneratedContracts(): Flow<List<GeneratedContract>>

    fun getAllReviewedContracts(): Flow<List<ReviewedContract>>

    suspend fun getReviewContract(timeStamp: Long): Result<ReviewedContract>

    suspend fun regenerateAnotherContract(complaints: String, generatedContract: GeneratedContract): Result<GeneratedContract>

    suspend fun reviewContract(contractContent: String): Result<ReviewedContract>
}