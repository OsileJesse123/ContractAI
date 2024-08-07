package com.jesse.contractai.ui.screens.splash

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesse.contractai.R
import com.jesse.contractai.ui.components.ContractAICircularProgressIndicator
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.ui.theme.Dimens
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
        modifier = Modifier.fillMaxSize()
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