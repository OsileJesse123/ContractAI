package com.jesse.contractai.core.config

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.jesse.contractai.core.model.ContractAiGenerationConfig
import com.jesse.contractai.core.model.GenerativeAIConfig
import org.json.JSONObject
import javax.inject.Inject

private const val GENERATIVE_AI_CONFIG_KEY = "generative_ai_config"

class FirebaseRemoteConfig @Inject constructor(): RemoteConfig {

    private val remoteConfig = FirebaseRemoteConfig.getInstance()

    private val defaultGenerativeAiConfigJson = """ {   "model_name": "gemini-3.8-flash",   "generation_config": {     "temperature": 0.4,     "top_k": 32,     "top_p": 1.0,     "max_output_tokens": 2048   },   "safety_settings": {     "HATE_SPEECH": "ONLY_HIGH",     "HARASSMENT": "ONLY_HIGH",     "DANGEROUS_CONTENT": "ONLY_HIGH"   } }"""

    init {
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(300)
            .build()
        remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(mapOf(GENERATIVE_AI_CONFIG_KEY to defaultGenerativeAiConfigJson))
        }
        fetchAndActivate()
    }

    fun fetchAndActivate(){
        remoteConfig.fetchAndActivate().addOnCompleteListener {
                _ ->
            // Do Nothing
        }
    }

    override fun getGenerationConfig(): ContractAiGenerationConfig{
        val root = JSONObject(remoteConfig.getString(GENERATIVE_AI_CONFIG_KEY))
        val generation = root.getJSONObject("generation_config")

        return ContractAiGenerationConfig(
            temperature = generation.getDouble("temperature").toFloat(),
            topK = generation.getInt("top_k"),
            topP = generation.getDouble("top_p").toFloat(),
            maxOutputTokens = generation.getInt("max_output_tokens"),
        )
    }

    override fun getGenerativeAIConfig(): GenerativeAIConfig {
        val root = JSONObject(remoteConfig.getString(GENERATIVE_AI_CONFIG_KEY))
        val generation = getGenerationConfig()
        Log.e("config", "generation config: $generation")
        val safety = root.getJSONObject("safety_settings")

        return GenerativeAIConfig(
            modelName = root.getString("model_name"),
            temperature = generation.temperature,
            topK = generation.topK,
            topP = generation.topP,
            maxOutputTokens = generation.maxOutputTokens,
            safetySettings = safety.keys().asSequence()
                .associateWith { key -> safety.getString(key) }
        )
    }
}