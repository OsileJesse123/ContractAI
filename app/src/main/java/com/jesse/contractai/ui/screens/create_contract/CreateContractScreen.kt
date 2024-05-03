package com.jesse.contractai.ui.screens.create_contract

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesse.contractai.R
import com.jesse.contractai.data.model.ContractOption
import com.jesse.contractai.ui.components.ContractAIRadioButton
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
        onNavigateBack,
        onContinue = {
            viewModel.onContinue()?.let {
                optionName ->
                onContinue(optionName)
            }
        }
    )
}

@Composable
fun CreateContractScreen(
    uiState: CreateContractUiState,
    onOptionSelected: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    onContinue: () -> Unit
) {

    Column(
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
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.border(width = 1.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = CircleShape)
            ) {
                Icon(painter = painterResource(
                    id = R.drawable.ic_arrow_back),
                    contentDescription = stringResource(id = R.string.arrow_back_icon),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
            ElevatedButton(
                onClick = onContinue,
                shape = MaterialTheme.shapes.small,
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 5.dp),
                contentPadding = PaddingValues(horizontal = 40.dp, vertical = 12.dp),
                enabled = uiState.isButtonEnabled(),
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
                )
            ),
            onOptionSelected = {},
            onNavigateBack = {},
            onContinue = {}
        )
    }
}