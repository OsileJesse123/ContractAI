package com.jesse.contractai.core.config

import com.jesse.contractai.core.model.ContractAiGenerationConfig
import com.jesse.contractai.core.model.GenerativeAIConfig

interface RemoteConfig {
    fun getGenerativeAIConfig(): GenerativeAIConfig
    fun getGenerationConfig(): ContractAiGenerationConfig
}