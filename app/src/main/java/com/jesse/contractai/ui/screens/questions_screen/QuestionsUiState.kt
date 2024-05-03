package com.jesse.contractai.ui.screens.questions_screen

import androidx.annotation.StringRes

data class QuestionsUiState(
    val question: Pair<Int, String> = Pair(-1, ""),
    val answer: String = "",
    val isLoading: Boolean = true,
    @StringRes
    val errorMessage: Int? = null,
    val navigateToNextScreen: Boolean = false
) {

    fun isAnswerValid(): Boolean = answer.isNotEmpty()
}
