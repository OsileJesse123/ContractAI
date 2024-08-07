package com.jesse.contractai.util

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.GenerationConfig
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import com.jesse.contractai.BuildConfig

object GenerativeAIModelUtil {

    const val GEMINI_1_5_PRO = "gemini-1.5-pro"
    val DEFAULT_GENERATION_CONFIG = generationConfig {
        temperature = 0.4f
        topK = 32
        topP = 1f
        maxOutputTokens = 2048
    }

    fun getGenerativeModel(modelName: String, generationConfig: GenerationConfig? = null): GenerativeModel{
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
    }
}