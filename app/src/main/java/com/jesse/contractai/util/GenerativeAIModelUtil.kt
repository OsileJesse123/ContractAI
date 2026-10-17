package com.jesse.contractai.util

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.GenerationConfig
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.Schema
import com.google.ai.client.generativeai.type.generationConfig
import com.jesse.contractai.BuildConfig
import com.jesse.contractai.core.model.ContractAiGenerationConfig
import com.jesse.contractai.core.model.GenerativeAIConfig

object GenerativeAIModelUtil {

    const val GEMINI_1_5_PRO = "gemini-1.5-pro"
    val DEFAULT_GENERATION_CONFIG = generationConfig {
        temperature = 0.4f
        topK = 32
        topP = 1f
        maxOutputTokens = 2048
    }

    /*fun getGenerativeModel(modelName: String, generationConfig: GenerationConfig? = null): GenerativeModel{
        return GenerativeModel(
            modelName = modelName,
            apiKey = BuildConfig.API_KEY,
            safetySettings = listOf(
                SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.ONLY_HIGH),
                SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.ONLY_HIGH),
                SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.ONLY_HIGH)
            ),
            generationConfig = generationConfig
        )
    }*/

    fun fromContractAiGenerationConfig(aiConfig: ContractAiGenerationConfig): GenerationConfig{
        return generationConfig {
            temperature = aiConfig.temperature
            topK = aiConfig.topK
            topP = aiConfig.topP
            maxOutputTokens = aiConfig.maxOutputTokens
        }
    }

    fun getGenerativeModel(
        aiConfig: GenerativeAIConfig,
        mimeType: String? = "application/json",
        schema: Schema<*>? = null,
        generationConfig: GenerationConfig? = null
    ): GenerativeModel {

        val config = generationConfig ?: generationConfig {
            temperature = aiConfig.temperature
            topK = aiConfig.topK
            topP = aiConfig.topP
            maxOutputTokens = aiConfig.maxOutputTokens

            if (schema != null) {
                responseMimeType = mimeType
                responseSchema = schema
            }
        }

        val safetySettings = aiConfig.safetySettings.mapNotNull {
                (category, threshold) ->
            val harmCategory = HarmCategory.entries
                .find { it.name == category }

            val blockThreshold = BlockThreshold.entries
                .find { it.name == threshold }

            if (harmCategory != null && blockThreshold != null) {
                SafetySetting(harmCategory, blockThreshold)
            } else {
                null
            }
        }

        return GenerativeModel(
            modelName = aiConfig.modelName,
            apiKey = BuildConfig.API_KEY,
            generationConfig = config,
            safetySettings = safetySettings
        )
    }
}