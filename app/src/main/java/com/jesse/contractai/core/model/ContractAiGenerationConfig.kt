package com.jesse.contractai.core.model

data class ContractAiGenerationConfig(
    val temperature: Float,
    val topK: Int,
    val topP: Float,
    val maxOutputTokens: Int
)
