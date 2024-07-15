package com.jesse.contractai.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesse.contractai.R
import com.jesse.contractai.ui.theme.ContractAITheme

@Composable
fun ContractAIButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val containerColor by animateColorAsState(
        targetValue = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        label = "button container color",
        animationSpec = tween(600)
    )

    ElevatedButton(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.extraSmall,
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 3.dp),
        contentPadding = PaddingValues(horizontal = 40.dp, vertical = 12.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = containerColor,
            disabledContainerColor = containerColor,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContentColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContractAIButtonPreview() {
    ContractAITheme {
        ContractAIButton(
            text = "Sign up",
            onClick = {},
        )
    }
}

