package com.jesse.contractai.ui.screens.contract_nature

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesse.contractai.R
import com.jesse.contractai.ui.components.ContractAIButton
import com.jesse.contractai.ui.components.ContractAIOutlineCircularButton
import com.jesse.contractai.ui.modifiers.dashedBorder
import com.jesse.contractai.ui.theme.ContractAITheme

@Composable
fun ContractNatureRoute(
    onNavigateBack: () -> Unit,
    onContinue: (String) -> Unit,
    onCancel: () -> Unit
) {
    ContractNatureScreen(
        onNavigateBack = onNavigateBack,
        onContinue = onContinue,
        onCancel = onCancel
    )
}

@Composable
fun ContractNatureScreen(
    onNavigateBack: () -> Unit,
    onContinue: (String) -> Unit,
    onCancel: () -> Unit
) {
    var answer by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 30.dp)

    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .weight(2f),
        ) {
            Text(
                text = stringResource(id = R.string.what_is_the_nature_of_your_contract),
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
                value = answer,
                textStyle = MaterialTheme.typography.titleMedium,
                onValueChange = {
                     answer = it
                },
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
                onClick = {
                    if (answer.isNotEmpty()){
                        onContinue(answer)
                    }
                },
                enabled = answer.isNotEmpty()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContractNatureScreenPreview() {
    ContractAITheme {
        ContractNatureScreen(
            onNavigateBack = {},
            onContinue = {},
            onCancel = {}
        )
    }
}