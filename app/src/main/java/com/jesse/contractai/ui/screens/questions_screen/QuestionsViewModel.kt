package com.jesse.contractai.ui.screens.questions_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.contractai.R
import com.jesse.contractai.data.repository.ContractRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val contractRepository: ContractRepository
): ViewModel() {

    private val _questionsUiState: MutableStateFlow<QuestionsUiState> = MutableStateFlow(
        QuestionsUiState()
    )
    val questionsUiState get() = _questionsUiState.asStateFlow()
    private var questions: List<Pair<Int, String>> = listOf()
    private var answers: MutableList<String> = mutableListOf()

    fun getQuestions(contractType: String, contractNature: String){
        viewModelScope.launch {
            val result = contractRepository.getMoreDetailsOnContract(contractType, contractNature)
            result.onSuccess {
                resultQuestions ->
                if (!resultQuestions.isNullOrEmpty()){
                    questions = resultQuestions.map {
                        Pair(resultQuestions.indexOf(it), it)
                    }
                    _questionsUiState.update {
                            questionsUiState ->
                        questionsUiState.copy(
                            isLoading = false,
                            question = questions[0]
                        )
                    }
                } else {
                    _questionsUiState.update {
                            questionsUiState ->
                        questionsUiState.copy(
                            isLoading = false,
                            errorMessage = R.string.no_questions
                        )
                    }
                }
            }.onFailure {
                _questionsUiState.update {
                        questionsUiState ->
                    questionsUiState.copy(
                        isLoading = false,
                        errorMessage = R.string.no_questions
                    )
                }
            }
        }
    }

    fun onAnswerChange(answer: String){
        _questionsUiState.update {
                questionsUiState ->
            questionsUiState.copy(
                answer = answer
            )
        }
    }

    fun onContinue(){
        if ((_questionsUiState.value.question.first + 1) < questions.size && _questionsUiState.value.isAnswerValid()){
            _questionsUiState.update {
                    questionsUiState ->
                answers.add(questionsUiState.answer)
                Log.e("Answer", "Answer: $answers")
                questionsUiState.copy(
                    question = questions[questionsUiState.question.first.plus(1)],
                    answer = ""
                )
            }
        }
        if(_questionsUiState.value.question.first + 1 == questions.size){
            _questionsUiState.update {
                    questionsUiState ->
                questionsUiState.copy(
                    navigateToNextScreen = true
                )
            }
        }
    }

    fun getAnswers(): List<String> = answers

    fun onNavigateToNextScreen(){
        _questionsUiState.update {
                questionsUiState ->
            questionsUiState.copy(
                navigateToNextScreen = false
            )
        }
    }
}