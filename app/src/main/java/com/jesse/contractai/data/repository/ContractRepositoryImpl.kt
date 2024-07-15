package com.jesse.contractai.data.repository

import android.util.Log
import com.jesse.contractai.data.exceptions.NoResponseException
import com.jesse.contractai.data.network.data_source.ContractNetworkDataSource
import com.jesse.contractai.di.ContractAIDispatchers
import com.jesse.contractai.di.Dispatcher
import com.jesse.contractai.ui.model.Questions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContractRepositoryImpl @Inject constructor(
    @Dispatcher(ContractAIDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val contractNetworkDataSource: ContractNetworkDataSource,
): ContractRepository {

    override suspend fun getMoreDetailsOnContract(contractType: String, contractNature: String): Result<List<String>> {
        return withContext(ioDispatcher){
            try {
                contractNetworkDataSource.getMoreContextOnContract(contractType, contractNature)?.let {
                    text ->
                    val questions = Questions.toQuestion(text)
                    Result.success(questions.questions)
                } ?: Result.failure(NoResponseException())
            } catch (e: Exception){
                Log.e("Error", "Error: $e")
                Result.failure(e)
            }
        }
    }

}