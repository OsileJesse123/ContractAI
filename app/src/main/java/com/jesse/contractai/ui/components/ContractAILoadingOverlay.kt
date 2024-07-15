package com.jesse.contractai.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesse.contractai.R
import com.jesse.contractai.ui.theme.ContractAITheme

@Composable
fun ContractAILoadingOverlay(
    modifier: Modifier = Modifier
){
    val infiniteTransition = rememberInfiniteTransition(label = "repeat loading overlay transition")
    val imageSize by infiniteTransition.animateFloat(
        initialValue = 50f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 300, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "repeat loading overlay transition"
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Gray.copy(alpha = .5f))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {

        Image(
            modifier = Modifier.size(imageSize.dp),
            painter = painterResource(
            id = R.drawable.ai_welcome_img),
            contentDescription = stringResource(id = R.string.ai_welcome_image)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContractAILoadingOverlayPreview() {
    ContractAITheme {
        ContractAILoadingOverlay()
    }
}