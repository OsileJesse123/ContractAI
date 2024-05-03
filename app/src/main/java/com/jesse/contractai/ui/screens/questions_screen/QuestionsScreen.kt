package com.jesse.contractai.ui.screens.questions_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesse.contractai.R
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.util.dashedBorder

@Composable
fun QuestionsRoute(
    contractType: String,
    contractNature: String,
    onNavigateToGenerateContract: (List<String>) -> Unit,
    viewModel: QuestionsViewModel = hiltViewModel()
) {
    val uiState by viewModel.questionsUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit){
        viewModel.getQuestions(contractType, contractNature)
    }
    LaunchedEffect(uiState.navigateToNextScreen){
        if (uiState.navigateToNextScreen){
            onNavigateToGenerateContract(viewModel.getAnswers())
            viewModel.onNavigateToNextScreen()
        }
    }
    QuestionsScreen(
        uiState,
        onValueChange = {
            viewModel.onAnswerChange(it)
        },
        onContinue = {
            viewModel.onContinue()
        }
    )
}

@Composable
fun QuestionsScreen(
    uiState: QuestionsUiState,
    onValueChange: (String) -> Unit,
    onContinue: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 30.dp)
    ) {
        if(uiState.isLoading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(
                    modifier = Modifier.wrapContentSize(),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        if(!uiState.isLoading && uiState.question.second.isNotEmpty()){
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .weight(2f),
            ) {
                Text(
                    text = uiState.question.second,
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 21.sp)
                )
                Spacer(modifier = Modifier.height(50.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
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
                    .weight(1f),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                ElevatedButton(
                    onClick = onContinue,
                    shape = MaterialTheme.shapes.small,
                    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 5.dp),
                    contentPadding = PaddingValues(horizontal = 40.dp, vertical = 12.dp),
                    enabled = uiState.isAnswerValid(),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContentColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.continue_),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }
        if (!uiState.isLoading && uiState.question.second.isEmpty() && uiState.errorMessage != null){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = stringResource(id = uiState.errorMessage),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 20.sp
                    )
                )
            }
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
                question = Pair(0, "What is this")
            ),
            onValueChange = {},
            onContinue = {}
        )
    }
}