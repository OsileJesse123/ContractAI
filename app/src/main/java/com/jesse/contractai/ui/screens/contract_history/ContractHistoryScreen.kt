package com.jesse.contractai.ui.screens.contract_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesse.contractai.R
import com.jesse.contractai.ui.components.ContractAIBackButton
import com.jesse.contractai.ui.components.ContractHistoryItem
import com.jesse.contractai.ui.model.ContractHistoryType
import com.jesse.contractai.ui.model.ContractResult
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.ui.theme.Dimens


private val contractHistory = listOf(
    ContractResult(id = "dkjfd", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "dfjdkf", type = ContractHistoryType.CONTRACT_REVIEW, title = "Review Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "ckvdfoe", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "cdfoe", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "dkdkf", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "cmcm", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "clz", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "czlc", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "eopr", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "dhafle", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "daeu", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "dda;f", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "dmafndkj", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "dkfalf", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "dfm;afdm", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "dfma;dfm", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "dsnfadj", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "dfna;fre", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "fnapee", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "daeijfre", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
    ContractResult(id = "dafj;a", type = ContractHistoryType.CONTRACT, title = "Employment Contract", content = "dkjfdkjfd", timeStamp = "20 July, 2024"),
)
@Composable
fun ContractHistoryRoute(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {
    ContractHistoryScreen(
        modifier = modifier,
        onNavigateBack = onNavigateBack,
        contractHistory = contractHistory
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContractHistoryScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
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
            LazyColumn(
                modifier = modifier.padding(paddingValues),
                contentPadding = PaddingValues(vertical = Dimens.screenVerticalPadding),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {

                items(
                    items = contractHistory,
                    key = {
                            contractResult ->
                        contractResult.id
                    }
                ){
                        contractResult ->
                    ContractHistoryItem(
                        icon = contractResult.getContractResultIcon(),
                        iconDescription = contractResult.getContractResultIconDescription(),
                        title = contractResult.title,
                        timeStamp = contractResult.timeStamp
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
            contractHistory = contractHistory,
            onNavigateBack = {}
        )
    }
}