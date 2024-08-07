package com.jesse.contractai.data.network.data_source

import com.jesse.contractai.data.network.model.GenerateContractResponse

interface ContractNetworkDataSource {
    suspend fun getMoreContextOnContract(contractType: String, contractNature: String): String?
    suspend fun generateContract(contractType: String, questionsAndAnswers: List<Pair<String, String>>): String?
    suspend fun regenerateAnotherContract(complaints: String, contractContent: String): String?
}