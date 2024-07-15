package com.jesse.contractai.ui.screens.generate_contract

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesse.contractai.R
import com.jesse.contractai.ui.modifiers.dashedBorder
import com.jesse.contractai.ui.theme.ContractAITheme

@Composable
fun GenerateContractRoute() {
    GenerateContractScreen(
        true
    )
}

@Composable
fun GenerateContractScreen(
    isLoading: Boolean
) {
    if (isLoading){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                strokeWidth = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
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
    if (!isLoading){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.your_contract_is_ready),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 26.sp
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.review_contract),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.tertiary
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        color = MaterialTheme.colorScheme.tertiary.copy(
                            alpha = .1f
                        )
                    )
                    .dashedBorder(
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = MaterialTheme.shapes.small
                    )
                    .verticalScroll(rememberScrollState()),

            ){
                Text(
                    text = "djfajfakja",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            ElevatedButton(
                onClick = {  },
                shape = MaterialTheme.shapes.small,
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 5.dp),
                contentPadding = PaddingValues(horizontal = 40.dp, vertical = 12.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.download),
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
fun GenerateContractScreenPreview() {
    ContractAITheme {
        GenerateContractScreen(
            isLoading = false
        )
    }
}