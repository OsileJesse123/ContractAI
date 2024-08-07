package com.jesse.contractai.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jesse.contractai.ui.theme.ContractAITheme

@Composable
fun ContractAICircularProgressIndicator(
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 7.dp,
    size: Dp = 70.dp
) {
    // Infinite transition to create continuous animation
    val infiniteTransition = rememberInfiniteTransition(label = "circular progress remember infinite")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 5f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "circular progress animation"
    )

    // Circular Progress Indicator UI
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.wrapContentSize()
    ) {
        Canvas(
            modifier = Modifier
                .size(size)
                .graphicsLayer(rotationZ = rotationAngle)
        ) {
            val gradientBrush = Brush.sweepGradient(
                colors = listOf(color.copy(alpha = .0f), color.copy(alpha = .5f), color)
            )
            drawArc(
                brush = gradientBrush,
                startAngle = 4.5f,
                sweepAngle = 350f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

@Preview
@Composable
private fun ContractAICircularProgressIndicatorPreview() {
    ContractAITheme {
        ContractAICircularProgressIndicator()
    }
}