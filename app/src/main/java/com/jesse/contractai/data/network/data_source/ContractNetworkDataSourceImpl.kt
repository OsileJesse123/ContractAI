package com.jesse.contractai.data.network.data_source

import com.google.ai.client.generativeai.type.Schema
import com.google.ai.client.generativeai.type.generationConfig
import com.jesse.contractai.data.network.model.GenerateContractResponse
import com.jesse.contractai.util.GenerativeAIModelUtil
import com.jesse.contractai.util.PromptUtil
import javax.inject.Inject

class ContractNetworkDataSourceImpl @Inject constructor(): ContractNetworkDataSource{
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
}