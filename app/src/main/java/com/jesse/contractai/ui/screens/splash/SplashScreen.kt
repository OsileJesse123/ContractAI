package com.jesse.contractai.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jesse.contractai.R
import com.jesse.contractai.ui.theme.ContractAITheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onTimeout: () -> Unit
) {

    val currentTimeout by rememberUpdatedState(newValue = onTimeout)

    LaunchedEffect(key1 = Unit) {
        delay(2_000L)
        currentTimeout()
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(id = R.drawable.animatedlogo),
            contentDescription = stringResource(id = R.string.contract_ai_logo)
        )
    }
}

@Preview(showBackground = true, heightDp = 700, widthDp = 320)
@Composable
fun SplashScreenPreview() {
    ContractAITheme {
        SplashScreen(
            onTimeout = {}
        )
    }
}