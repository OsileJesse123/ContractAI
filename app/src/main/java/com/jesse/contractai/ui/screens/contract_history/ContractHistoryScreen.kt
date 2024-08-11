package com.jesse.contractai.ui.screens.contract_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesse.contractai.R
import com.jesse.contractai.ui.components.ContractAIBackButton
import com.jesse.contractai.ui.components.ContractHistoryItem
import com.jesse.contractai.ui.model.ContractHistoryType
import com.jesse.contractai.ui.model.ContractResult
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.ui.theme.Dimens


@Composable
fun ContractHistoryRoute(
    modifier: Modifier = Modifier,
    onNavigateToGeneratedContractDetails: (Long) -> Unit,
    onNavigateToReviewedContractDetails: (Long) -> Unit,
    viewModel: ContractHistoryViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val contractResults by viewModel.contractHistoryItems.collectAsStateWithLifecycle()

    ContractHistoryScreen(
        modifier = modifier,
        onNavigateBack = onNavigateBack,
        contractHistory = contractResults,
        onNavigateToGeneratedContractDetails = onNavigateToGeneratedContractDetails,
        onNavigateToReviewedContractDetails = onNavigateToReviewedContractDetails
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContractHistoryScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onNavigateToReviewedContractDetails: (Long) -> Unit,
    onNavigateToGeneratedContractDetails: (Long) -> Unit,
    contractHistory: List<ContractResult>
) {
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehaviour.nestedScrollConnection)
            .background(color = MaterialTheme.colorScheme.background)
            .padding(
                start = Dimens.screenHorizontalPadding,
                end = Dimens.screenHorizontalPadding,
                top = Dimens.screenVerticalPadding
            ),
        topBar = {
            MediumTopAppBar(
                navigationIcon = {
                    ContractAIBackButton(
                        modifier = Modifier,
                        onClick = onNavigateBack
                    )
                },
                title = {
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.contract_history),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 18.sp
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                scrollBehavior = scrollBehaviour
            )
        }
    ) {
        paddingValues ->
        if(contractHistory.isNotEmpty()){
            LazyColumn(
                modifier = modifier.padding(paddingValues),
                contentPadding = PaddingValues(vertical = Dimens.screenVerticalPadding),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {

                items(
                    items = contractHistory,
                    key = {
                            contractResult ->
                        contractResult.timeStamp
                    }
                ){
                        contractResult ->
                    ContractHistoryItem(
                        icon = contractResult.getContractResultIcon(),
                        iconDescription = contractResult.getContractResultIconDescription(),
                        title = contractResult.title,
                        timeStamp = contractResult.getFormattedTime(),
                        onClick = {
                            when(contractResult){
                                is ContractResult.GeneratedContractResult -> {
                                    onNavigateToGeneratedContractDetails(contractResult.timeStamp)
                                }
                                is ContractResult.ReviewedContractResult -> {
                                    onNavigateToReviewedContractDetails(contractResult.timeStamp)
                                }
                            }
                        }
                    )
                }
            }
        } else{
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center){
                Text(
                    text = stringResource(id = R.string.no_contract_history),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun ContractHistoryScreenPreview() {
    ContractAITheme {
        ContractHistoryScreen(
            contractHistory = listOf(),
            onNavigateBack = {},
            onNavigateToGeneratedContractDetails = {},
            onNavigateToReviewedContractDetails = {}
        )
    }
}