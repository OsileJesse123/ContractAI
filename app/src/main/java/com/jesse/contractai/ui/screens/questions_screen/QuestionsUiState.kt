package com.jesse.contractai.ui.screens.questions_screen

import androidx.annotation.StringRes
import androidx.compose.ui.text.input.TextFieldValue

data class QuestionsUiState(
    val question: Pair<Int, String> = Pair(-1, ""),
    val answer: TextFieldValue = TextFieldValue(""),
    val isLoading: Boolean = true,
    @StringRes
    val errorMessage: Int? = null,
    val navigateToNextScreen: Boolean = false,
    val navigateToPreviousScreen: Boolean = false
) {

    fun isAnswerValid(): Boolean = answer.text.isNotEmpty()
}
