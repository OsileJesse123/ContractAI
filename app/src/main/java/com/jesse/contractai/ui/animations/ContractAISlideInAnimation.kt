package com.jesse.contractai.ui.animations

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun ContractAISlideInAnimation(
    modifier: Modifier = Modifier,
    offsetAnimationDelay: Long = 300,
    direction: SlideDirection,
    beginAnimation: Boolean = false,
    animationSpec: AnimationSpec<Dp> = tween(easing = LinearEasing),
    content: @Composable (Modifier) -> Unit
) {
    var startAnimation by remember{
        mutableStateOf(beginAnimation)
    }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val offsetX: Dp by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else when(direction){
            SlideDirection.START -> -screenWidth
            SlideDirection.END -> screenWidth
            else -> 0.dp
        },
        animationSpec = animationSpec,
        label = "offsetX"
    )

    val offsetY: Dp by animateDpAsState(
        targetValue = if(startAnimation) 0.dp else when(direction){
            SlideDirection.TOP -> -screenHeight
            SlideDirection.BOTTOM -> screenHeight
            else -> 0.dp
        },
        animationSpec = animationSpec,
        label = "offsetY"

    )
    
    LaunchedEffect(Unit) {
        delay(offsetAnimationDelay)
        startAnimation = true
    }

    content(modifier.offset(x = offsetX, y = offsetY))
}

enum class SlideDirection{
    START,
    END,
    TOP,
    BOTTOM,
}