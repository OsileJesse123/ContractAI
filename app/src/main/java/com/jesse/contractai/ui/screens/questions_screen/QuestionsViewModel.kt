package com.jesse.contractai.ui.screens.questions_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.contractai.R
import com.jesse.contractai.data.repository.contract.ContractRepository
import com.jesse.contractai.di.ContractAIDispatchers
import com.jesse.contractai.di.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val contractRepository: ContractRepository,
    @Dispatcher(ContractAIDispatchers.DEFAULT) private val defaultDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _questionsUiState: MutableStateFlow<QuestionsUiState> = MutableStateFlow(
        QuestionsUiState()
    )
    val questionsUiState get() = _questionsUiState.asStateFlow()
    var generatedContractTimeStamp: Long = 1
        private set

    var questions: List<Pair<Int, String>> = listOf()
        private set
    private var answers: MutableList<String> = mutableListOf()

    var contractType = ""
        set(value) {
            if (value.isNotEmpty())
                field = value
        }

    private fun generateContract(){
        viewModelScope.launch {
            _questionsUiState.update {
                    questionsUiState ->
                questionsUiState.copy(
                    isLoading = true
                )
            }
            val questionsAndAnswers = withContext(defaultDispatcher){
                questions.map {
                    question ->
                    Log.e("QuestionPair", "QuestionPair: $question")
                    Pair(
                        question.second,
                        answers[question.first]
                    )
                }
            }
            val result = contractRepository.generateContract(
                contractType = contractType,
                questionsAndAnswers = questionsAndAnswers
            )
            result
                .onSuccess {
                    contractResponse ->
                    generatedContractTimeStamp = contractResponse.timeStamp
                    _questionsUiState.update {
                            questionsUiState ->
                        questionsUiState.copy(
                            navigateToNextScreen = true
                        )
                    }
                }
                .onFailure {
                    _questionsUiState.update {
                            questionsUiState ->
                        questionsUiState.copy(
                            isLoading = false,
                            errorMessage = R.string.failed_to_generate_contract
                        )
                    }
                }

        }
    }

    fun getQuestions(contractType: String, contractNature: String){
        viewModelScope.launch {
            val result = contractRepository.getMoreDetailsOnContract(contractType, contractNature)
            result.onSuccess {
                resultQuestions ->
                if (resultQuestions.isNotEmpty()){
                    questions = resultQuestions.map {
                        Pair(resultQuestions.indexOf(it), it)
                    }
                    Log.e("Questions", "Questions: $questions")
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

    fun onAnswerChange(answer: TextFieldValue){
        _questionsUiState.update {
                questionsUiState ->
            Log.e("AnswerText","OnAnswerChange: $answer")
            questionsUiState.copy(
                answer = answer
            )
        }
    }

    fun onContinue(){
        val currentQuestionIndex = _questionsUiState.value.question.first
        if ((currentQuestionIndex + 1) < questions.size && _questionsUiState.value.isAnswerValid()){
            _questionsUiState.update {
                    questionsUiState ->
                try {
                    answers[currentQuestionIndex] = questionsUiState.answer.text
                } catch (e: Exception){
                    answers.add(questionsUiState.answer.text)
                }
                val nextAnswer = try { answers[currentQuestionIndex + 1] } catch (e: Exception){""}
                questionsUiState.copy(
                    question = questions[currentQuestionIndex.plus(1)],
                    answer = TextFieldValue(nextAnswer, TextRange(nextAnswer.length))
                )
            }
        }
        if(currentQuestionIndex + 1 == questions.size && _questionsUiState.value.isAnswerValid()){
            try {
                answers[currentQuestionIndex] = _questionsUiState.value.answer.text
            } catch (e: Exception){
                answers.add(_questionsUiState.value.answer.text)
            }
            generateContract()
        }
    }

    fun onNavigateToPreviousQuestion(){
        val questionIndex = _questionsUiState.value.question.first
        if((questionIndex - 1) >= 0){
            _questionsUiState.update {
                questionsUiState ->
                val previousAnswer = answers[questionIndex.minus(1)]
                questionsUiState.copy(
                    question = questions[questionIndex.minus(1)],
                    answer = TextFieldValue(previousAnswer, selection = TextRange(previousAnswer.length))
                )
            }
        } else {
            _questionsUiState.update {
                    questionsUiState ->
                questionsUiState.copy(
                    navigateToPreviousScreen = true
                )
            }
        }
    }

    fun onNavigateToNextScreen(){
        _questionsUiState.update {
                questionsUiState ->
            questionsUiState.copy(
                navigateToNextScreen = false
            )
        }
    }

    fun onErrorMessageShown(){
        _questionsUiState.update {
                questionsUiState ->
            questionsUiState.copy(
                errorMessage = null
            )
        }
    }
}