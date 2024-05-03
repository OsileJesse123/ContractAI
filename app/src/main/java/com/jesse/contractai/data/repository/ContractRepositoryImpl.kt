package com.jesse.contractai.data.repository

import android.content.Context
import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.Gson
import com.jesse.contractai.data.model.ContractJson
import com.jesse.contractai.data.model.Questions
import com.jesse.contractai.di.ContractAIDispatchers
import com.jesse.contractai.di.Dispatcher
import com.jesse.contractai.util.NoResponseException
import com.jesse.contractai.util.getPrompt
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContractRepositoryImpl @Inject constructor(
    @Dispatcher(ContractAIDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val generativeModel: GenerativeModel
): ContractRepository {

    override suspend fun getMoreDetailsOnContract(contractType: String, contractNature: String): Result<List<String>> {
        return withContext(ioDispatcher){
            try {
                val prompt = getPrompt(contractType, contractNature)
                val response = generativeModel.generateContent(prompt)
                response.text?.let {
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