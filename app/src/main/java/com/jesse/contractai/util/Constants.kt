package com.jesse.contractai.util

import androidx.annotation.StringRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.Dp
import com.jesse.contractai.R

const val animationDelayTime = 300L
const val animationDelayTimeDifference = 100
val animationSpec = tween<Dp>(durationMillis = 500, easing = LinearEasing)
const val SPLIT_FIRST_AND_LAST_NAME_WITH_WHITESPACE = " "
@StringRes
val resetPasswordSuccessMessage = R.string.reset_password_email