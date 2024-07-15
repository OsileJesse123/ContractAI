package com.jesse.contractai.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import com.jesse.contractai.ui.theme.ContractAITheme
import kotlinx.coroutines.launch

@Composable
fun ContractAIErrorText(
    modifier: Modifier = Modifier,
    text: String?
) {

    AnimatedVisibility(
        visible = text != null,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Text(
            modifier = modifier,
            text = text ?: "",
            style = MaterialTheme.typography.titleSmall.copy(
                color = MaterialTheme.colorScheme.error
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContractAIErrorTextPreview() {
    ContractAITheme {
        ContractAIErrorText(
            text = "Invalid email"
        )
    }
}