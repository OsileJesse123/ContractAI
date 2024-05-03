package com.jesse.contractai.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesse.contractai.ui.theme.ContractAITheme

@Composable
fun ContractAISnackBar(
    modifier: Modifier = Modifier,
    cardColor: Color,
    message: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
        ,
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            text = message,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, heightDp = 320, widthDp = 320)
@Composable
fun ContractAISnackBarPreview() {
    ContractAITheme {
        ContractAISnackBar(
            cardColor = MaterialTheme.colorScheme.primary,
            message = "Feature is not available"
        )
    }
}