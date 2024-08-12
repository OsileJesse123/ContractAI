package com.jesse.contractai.data.network.data_source

import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.Schema
import com.google.ai.client.generativeai.type.generationConfig
import com.jesse.contractai.data.network.model.GenerateContractResponse
import com.jesse.contractai.util.GenerativeAIModelUtil
import com.jesse.contractai.util.PromptUtil
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContractNetworkDataSourceImpl @Inject constructor(): ContractNetworkDataSource{

    private var chat: Chat? = null
    override suspend fun getMoreContextOnContract(contractType: String, contractNature: String): String? {
        val jsonSchema = Schema.arr("questions", "A list of questions regarding the contract", Schema.str("question", "a question"))
        val generativeModel = GenerativeAIModelUtil
            .getGenerativeModel(
                GenerativeAIModelUtil.GEMINI_1_5_PRO,
                generationConfig {
                    temperature = 0.4f
                    topK = 32
                    topP = 1f
                    maxOutputTokens = 2048
                    responseMimeType = "application/json"
                    responseSchema = jsonSchema
                }
            )
        val prompt = PromptUtil.generateContractContextQuestions(contractType, contractNature)
        val response = generativeModel.generateContent(prompt)
        return response.text
    }

    override suspend fun generateContract(
        contractType: String,
        questionsAndAnswers: List<Pair<String, String>>
    ): String? {
        val jsonSchema =
            Schema.obj(
                "generate_contract_response",
                "the generated contract object",
                Schema.str("title", "the title of the contract generated"),
                Schema.str("content", "the actual generated contract content")
            )
        val generativeModel = GenerativeAIModelUtil
            .getGenerativeModel(
                GenerativeAIModelUtil.GEMINI_1_5_PRO,
                generationConfig {
                    temperature = 0.4f
                    topK = 32
                    topP = 1f
                    maxOutputTokens = 2048
                    responseMimeType = "application/json"
                    responseSchema = jsonSchema
                }
            )
        val prompt = PromptUtil.generateContract(contractType, questionsAndAnswers)
        val response = generativeModel.generateContent(prompt)
        return response.text
    }

    override suspend fun regenerateAnotherContract(
        complaints: String,
        contractContent: String
    ): String? {
        val jsonSchema =
            Schema.obj(
                "generate_contract_response",
                "the generated contract object",
                Schema.str("title", "the title of the contract generated"),
                Schema.str("content", "the actual generated contract content")
            )
        val generativeModel = GenerativeAIModelUtil
            .getGenerativeModel(
                GenerativeAIModelUtil.GEMINI_1_5_PRO,
                generationConfig {
                    temperature = 0.4f
                    topK = 32
                    topP = 1f
                    maxOutputTokens = 2048
                    responseMimeType = "application/json"
                    responseSchema = jsonSchema
                }
            )
        val prompt = PromptUtil.regenerateAnotherContract(complaints, contractContent)
        val response = generativeModel.generateContent(prompt)
        return response.text
    }

    override suspend fun reviewContract(contractContent: String): String? {
        val jsonSchema =
            Schema.obj(
                "review contract response",
                "the reviewed contract object",
                Schema.str("title", "the title of the contract reviewed"),
                Schema.str("content", "the review of the contract as given by gemini")
            )
        val generativeModel = GenerativeAIModelUtil
            .getGenerativeModel(
                GenerativeAIModelUtil.GEMINI_1_5_PRO,
                generationConfig {
                    temperature = 0.4f
                    topK = 32
                    topP = 1f
                    maxOutputTokens = 2048
                    responseMimeType = "application/json"
                    responseSchema = jsonSchema
                }
            )
        val prompt = PromptUtil.reviewContract(contractContent)
        val response = generativeModel.generateContent(prompt)
        return response.text
    }

    override fun initializeChat(history: List<Content>) {
        chat = GenerativeAIModelUtil.getGenerativeModel(
            modelName = GenerativeAIModelUtil.GEMINI_1_5_PRO,
            generationConfig = GenerativeAIModelUtil.DEFAULT_GENERATION_CONFIG
        ).startChat(history = history)
    }

    override suspend fun sendMessage(message: String): String? {
        return chat?.sendMessage(message)?.text
    }

    override fun isChatInitialized(): Boolean = chat != null

    override fun resetChat() {
        chat = null
    }
}