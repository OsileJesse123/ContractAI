package com.jesse.contractai.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesse.contractai.data.model.ContractOption
import com.jesse.contractai.ui.theme.ContractAITheme

@Composable
fun ContractAIRadioButton(
    modifier: Modifier = Modifier,
    contractOption: ContractOption,
    index: Int,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = contractOption.isSelected,
            onClick = { onClick(index) },
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = contractOption.optionName,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContractAIRadioButtonScreen() {
    ContractAITheme {
        ContractAIRadioButton(
            contractOption = ContractOption(isSelected = false, optionName = "Employment Contract"),
            index = 0,
            onClick = {

            }
        )
    }
}