package com.jesse.contractai.util

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.Dp

const val animationDelayTime = 300L
const val animationDelayTimeDifference = 100
val animationSpec = tween<Dp>(durationMillis = 500, easing = LinearEasing)
