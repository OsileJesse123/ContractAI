package com.jesse.contractai.ui.model

import com.google.gson.Gson

data class Questions(
    val questions: List<String>
) {
    companion object {
        fun toQuestion(jsonFormat: String?): Questions = Gson().fromJson(jsonFormat, Questions::class.java)
    }
}
