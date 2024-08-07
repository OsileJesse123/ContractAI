package com.jesse.contractai.ui.screens.create_contract

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesse.contractai.R
import com.jesse.contractai.data.network.model.ContractOption
import com.jesse.contractai.ui.components.ContractAIButton
import com.jesse.contractai.ui.components.ContractAIOutlineCircularButton
import com.jesse.contractai.ui.components.ContractAIRadioButton
import com.jesse.contractai.ui.modifiers.dashedBorder
import com.jesse.contractai.ui.theme.ContractAITheme

@Composable
fun CreateContractRoute(
    viewModel: CreateContractViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onContinue: (String) -> Unit
) {
    val uiState by viewModel.createContractUiState.collectAsStateWithLifecycle()
    CreateContractScreen(
        uiState = uiState,
        onOptionSelected = {
            viewModel.onOptionSelected(it)
        },
        onNavigateBack = onNavigateBack,
        onContinue = {
            viewModel.onContinue()?.let {
                optionName ->
                onContinue(optionName)
            }
        },
        onOthersDescriptionChanged = {
            viewModel.onOthersDescriptionChange(it)
        }
    )
}

@Composable
fun CreateContractScreen(
    uiState: CreateContractUiState,
    onOptionSelected: (Int) -> Unit,
    onOthersDescriptionChanged: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onContinue: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 30.dp)
    ) {
        Column(
            modifier = Modifier.wrapContentSize()
        ) {
            Text(
                text = stringResource(id = R.string.what_type_of_contract_do_you_need),
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 21.sp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            uiState.contractOptions.forEach {
                ContractAIRadioButton(
                    contractOption = it,
                    index = uiState.contractOptions.indexOf(it),
                    onClick = {
                            index ->
                        onOptionSelected(index)
                    }
                )
                Spacer(modifier = Modifier.height(25.dp))
            }
            AnimatedVisibility(visible = uiState.showOthersTextField()) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .dashedBorder(
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = MaterialTheme.shapes.small
                        ),
                    value = uiState.othersDescription,
                    textStyle = MaterialTheme.typography.titleMedium,
                    onValueChange = onOthersDescriptionChanged,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.specify_your_contract_type_here),
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
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            ContractAIOutlineCircularButton(
                color = MaterialTheme.colorScheme.error,
                icon = R.drawable.ic_cancel,
                iconDescription = R.string.cancel_icon,
                onClick = onNavigateBack
            )

            ContractAIButton(
                modifier = Modifier.fillMaxWidth(.6f),
                text = stringResource(id = R.string.continue_),
                onClick = onContinue,
                enabled = uiState.isButtonEnabled()
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CreateContractScreenPreview() {
    ContractAITheme {
        CreateContractScreen(
            uiState = CreateContractUiState(
                contractOptions = listOf(
                    ContractOption(isSelected = false, "Employment Contract"),
                    ContractOption(isSelected = false, "Non Disclosure Agrement"),
                    ContractOption(isSelected = false, "Intellectual Property Agreement"),
                    ContractOption(isSelected = false, "Others"),
                )
            ),
            onOptionSelected = {},
            onNavigateBack = {},
            onContinue = {},
            onOthersDescriptionChanged = {}
        )
    }
}