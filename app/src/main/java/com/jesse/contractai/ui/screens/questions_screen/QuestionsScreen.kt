package com.jesse.contractai.ui.screens.questions_screen

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesse.contractai.R
import com.jesse.contractai.ui.components.ContractAIButton
import com.jesse.contractai.ui.components.ContractAICircularProgressIndicator
import com.jesse.contractai.ui.components.ContractAIOutlineCircularButton
import com.jesse.contractai.ui.components.ContractAISnackBar
import com.jesse.contractai.ui.modifiers.dashedBorder
import com.jesse.contractai.ui.theme.ContractAITheme

@Composable
fun QuestionsRoute(
    contractType: String,
    contractNature: String,
    onCancel: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToGenerateContract: (Long) -> Unit,
    viewModel: QuestionsViewModel = hiltViewModel()
) {
    val uiState by viewModel.questionsUiState.collectAsStateWithLifecycle()
    val questionsSize by rememberUpdatedState(newValue = viewModel.questions.size)

    LaunchedEffect(Unit){
        with(viewModel){
            getQuestions(contractType, contractNature)
            this.contractType = contractType
        }
    }
    LaunchedEffect(uiState.navigateToNextScreen){
        if (uiState.navigateToNextScreen){
            onNavigateToGenerateContract(viewModel.generatedContractTimeStamp)
            viewModel.onNavigateToNextScreen()
        }
    }
    LaunchedEffect(uiState.navigateToPreviousScreen) {
        if (uiState.navigateToPreviousScreen){
            onNavigateBack()
        }
    }
    QuestionsScreen(
        uiState = uiState,
        questionsSize = questionsSize,
        onValueChange = {
            Log.e("AnswerText", "Text: ${it.text}")
            viewModel.onAnswerChange(it)
        },
        onContinue = {
            viewModel.onContinue()
        },
        onCancel = onCancel,
        onNavigateBack = {
            viewModel.onNavigateToPreviousQuestion()
        },
        onSnackBarShown = {
            viewModel.onErrorMessageShown()
        }
    )
}

@Composable
fun QuestionsScreen(
    uiState: QuestionsUiState,
    questionsSize: Int,
    onSnackBarShown: () -> Unit,
    onValueChange: (TextFieldValue) -> Unit,
    onContinue: () -> Unit,
    onCancel: () -> Unit,
    onNavigateBack: () -> Unit
) {
    AnimatedContent(
        targetState = uiState.isLoading,
        label = "questions screen content animation"
    ) {
        isLoading ->
        if(isLoading){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                ContractAICircularProgressIndicator()
                // If the user has gotten to the last question, this means the user is attempting
                // to generate a contract so text is displayed to inform the user.
                if(uiState.question.first == (questionsSize - 1) && uiState.question.first != -1){
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.generating_contract),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    )
                }
            }
        }
        if(!isLoading && uiState.question.second.isNotEmpty()){
            val scrollState = rememberScrollState()
            LaunchedEffect(key1 = uiState.question) {
                if (scrollState.canScrollBackward){
                    scrollState.animateScrollTo(0)
                }
            }
            Box(modifier = Modifier.fillMaxSize()){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 30.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                            .fillMaxSize()
                            .weight(2f),
                    ) {
                        Text(
                            text = stringResource(id = R.string.question_number_range, (uiState.question.first + 1), questionsSize),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = uiState.question.second,
                            style = MaterialTheme.typography.titleSmall.copy(fontSize = 21.sp)
                        )
                        Spacer(modifier = Modifier.height(50.dp))
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .dashedBorder(
                                    color = MaterialTheme.colorScheme.tertiary,
                                    shape = MaterialTheme.shapes.small
                                ),
                            value = uiState.answer,
                            textStyle = MaterialTheme.typography.titleMedium,
                            onValueChange = onValueChange,
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.type_your_answer_here),
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.onSecondary.copy(
                                            alpha = .5f
                                        )
                                    )
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.tertiary.copy(
                                    alpha = .1f
                                ),
                                unfocusedContainerColor = MaterialTheme.colorScheme.tertiary.copy(
                                    alpha = .1f
                                ),
                                focusedIndicatorColor = Color.Transparent, // Specify the focused indicator color
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .weight(.5f),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            ContractAIOutlineCircularButton(
                                color = MaterialTheme.colorScheme.error,
                                icon = R.drawable.ic_cancel,
                                iconDescription = R.string.cancel_icon,
                                onClick = onCancel
                            )
                            ContractAIOutlineCircularButton(
                                color = MaterialTheme.colorScheme.tertiary,
                                icon = R.drawable.ic_arrow_back,
                                iconDescription = R.string.arrow_back_icon,
                                onClick = onNavigateBack
                            )
                        }
                        ContractAIButton(
                            modifier = Modifier.fillMaxWidth(.7f),
                            text = stringResource(id = R.string.continue_),
                            onClick = onContinue,
                            enabled = uiState.isAnswerValid()
                        )
                    }

                }
                ContractAISnackBar(
                    modifier = Modifier,
                    message = uiState.errorMessage,
                    onSnackBarShown = onSnackBarShown
                )
            }
        }
    }
    if (!uiState.isLoading && uiState.question.second.isEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = stringResource(id = R.string.no_questions),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 20.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionsScreenPreview() {
    ContractAITheme {
        QuestionsScreen(
            QuestionsUiState(
                isLoading = false,
                question = Pair(0, "What is the nature of your contract")
            ),
            onValueChange = {},
            onContinue = {},
            onCancel = {},
            onNavigateBack = {},
            questionsSize = 20,
            onSnackBarShown = {}
        )
    }
}