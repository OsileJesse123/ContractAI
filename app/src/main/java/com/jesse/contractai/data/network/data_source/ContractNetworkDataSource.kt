package com.jesse.contractai.data.network.data_source

import com.google.ai.client.generativeai.type.Content

interface ContractNetworkDataSource {
    suspend fun getMoreContextOnContract(contractType: String, contractNature: String): String?
    suspend fun generateContract(contractType: String, questionsAndAnswers: List<Pair<String, String>>): String?
    suspend fun regenerateAnotherContract(complaints: String, contractContent: String): String?
    suspend fun reviewContract(contractContent: String): String?
    fun initializeChat(history: List<Content>)
    fun isChatInitialized(): Boolean

    suspend fun sendMessage(message: String): String?
    fun resetChat()
}