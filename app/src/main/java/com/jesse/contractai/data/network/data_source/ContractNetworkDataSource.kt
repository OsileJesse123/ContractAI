package com.jesse.contractai.data.network.data_source

import com.google.ai.client.generativeai.GenerativeModel
import com.jesse.contractai.data.exceptions.NoResponseException
import com.jesse.contractai.ui.model.Questions
import com.jesse.contractai.util.getPrompt
import javax.inject.Inject

class ContractNetworkDataSource @Inject constructor(
    private val generativeModel: GenerativeModel
){
    suspend fun getMoreContextOnContract(contractType: String, contractNature: String): String? {
        val prompt = getPrompt(contractType, contractNature)
        val response = generativeModel.generateContent(prompt)
        return response.text
    }
}