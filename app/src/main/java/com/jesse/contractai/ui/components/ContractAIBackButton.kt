package com.jesse.contractai.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jesse.contractai.R
import com.jesse.contractai.ui.theme.ContractAITheme

@Composable
fun ContractAIBackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .clip(CircleShape),
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(
                alpha = .1f
            )
        )
    ) {
        Icon(
            painter = painterResource(
                id = R.drawable.ic_double_back_arrow
            ),
            tint = MaterialTheme.colorScheme.tertiary,
            contentDescription = stringResource(id = R.string.double_back_arrow)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ContractAIBackButtonPreview() {
    ContractAITheme {
        ContractAIBackButton(
            onClick = {}
        )
    }
}