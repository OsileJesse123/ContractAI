package com.jesse.contractai.di

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import com.jesse.contractai.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AIModelModule {

    @Provides
    @Singleton
    fun provideGenerativeAIModel() = GenerativeModel(
        modelName = "gemini-1.0-pro-latest",
        apiKey = BuildConfig.API_KEY,
        safetySettings = listOf(
            SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.ONLY_HIGH),
            SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.ONLY_HIGH),
            SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.ONLY_HIGH)
        ),
        generationConfig = generationConfig {
            temperature = 0.4f
            topK = 32
            topP = 1f
            maxOutputTokens = 2048
        }
    )
}