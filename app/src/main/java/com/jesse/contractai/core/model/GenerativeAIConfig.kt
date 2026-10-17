package com.jesse.contractai.core.model

data class GenerativeAIConfig(
    val modelName: String,
    val temperature: Float,
    val topK: Int,
    val topP: Float,
    val maxOutputTokens: Int,
    val safetySettings: Map<String, String>
)
