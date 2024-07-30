package com.jesse.contractai.ui.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.R
import kotlinx.coroutines.delay

@Composable
fun ContractAISnackBar(
    modifier: Modifier = Modifier,
    @StringRes
    message: Int?,
    delayTime: Long = 2_000,
    onSnackBarShown: () -> Unit,
    barColor: Color = MaterialTheme.colorScheme.error
) {
    val currentOnSnackBarShown by rememberUpdatedState(newValue = onSnackBarShown)
    LaunchedEffect(message) {
        delay(delayTime)
        currentOnSnackBarShown()
    }
    AnimatedVisibility(
        visible = message != null,
        enter = slideInVertically(
            // Enters by sliding in from offset -fullHeight to 0.
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            // Exits by sliding out from offset 0 to -fullHeight.
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
        )
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(
                containerColor = barColor,
            ),
            shape = RectangleShape
        ) {
            message?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    text = stringResource(id = it),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 14.sp, color = Color.White
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 320, widthDp = 320)
@Composable
fun ContractAISnackBarPreview() {
    ContractAITheme {
        ContractAISnackBar(
            message = R.string.network_error_occured,
            onSnackBarShown = {}
        )
    }
}