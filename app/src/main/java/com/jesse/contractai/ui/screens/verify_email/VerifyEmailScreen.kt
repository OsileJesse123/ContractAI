package com.jesse.contractai.ui.screens.verify_email

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesse.contractai.R
import com.jesse.contractai.ui.animations.ContractAISlideInAnimation
import com.jesse.contractai.ui.animations.SlideDirection
import com.jesse.contractai.ui.components.ContractAIBackButton
import com.jesse.contractai.ui.components.ContractAIButton
import com.jesse.contractai.ui.components.ContractAILoadingOverlay
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.ui.theme.Dimens
import com.jesse.contractai.util.animationDelayTime
import com.jesse.contractai.util.animationDelayTimeDifference
import com.jesse.contractai.util.animationSpec

@Composable
fun VerifyEmailRoute(
    modifier: Modifier = Modifier,
    onNavigateToNextScreen: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: VerifyEmailViewModel = hiltViewModel()
) {
    val uiState by viewModel.verifyEmailUiState.collectAsStateWithLifecycle()

    VerifyEmailScreen(
        modifier = modifier,
        beginAnimation = false,
        onNavigateBack = onNavigateBack,
        onNavigateToNextScreen = onNavigateToNextScreen,
        onResendEmailLink = {
           viewModel.sendUiAction(VerifyEmailUiAction.Send)
        },
        uiState = uiState
    )
}

@Composable
fun VerifyEmailScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onNavigateToNextScreen: () -> Unit,
    onResendEmailLink: () -> Unit,
    uiState: VerifyEmailUiState,
    beginAnimation: Boolean
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = Dimens.screenHorizontalPadding,
                    end = Dimens.screenHorizontalPadding,
                    top = Dimens.screenVerticalPadding
                )
        ) {

            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.TOP,
                animationSpec = tween(durationMillis = 500),
                beginAnimation = beginAnimation
            ) {
                ContractAIBackButton(
                    modifier = it,
                    onClick = onNavigateBack
                )
            }
            Spacer(modifier = Modifier.height(height = 20.dp))
            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.START,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 1),
                beginAnimation = beginAnimation
            ) {
                Text(
                    modifier = it,
                    text = stringResource(id = R.string.verify_email),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 18.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(height = 50.dp))
            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.START,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 2),
                beginAnimation = beginAnimation
            ) {
                Image(
                    modifier = it
                        .fillMaxWidth()
                        .size(100.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary),
                    painter = painterResource(id = R.drawable.ic_mail_open),
                    contentDescription = stringResource(id = R.string.open_email_image)
                )
            }
            Spacer(modifier = Modifier.height(height = Dimens.marginMedium))
            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.START,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 3),
                beginAnimation = beginAnimation
            ) {
                Text(
                    modifier = it.fillMaxWidth(),
                    text = stringResource(id = R.string.verify_your_email_address),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(height = Dimens.marginMedium))
            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.END,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 3),
                beginAnimation = beginAnimation
            ) {
                Text(
                    modifier = it.fillMaxWidth(),
                    text = stringResource(id = R.string.verify_email_address_instructions),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 14.sp,
                        lineHeight = 24.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(height = 20.dp))
            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.END,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 4),
                beginAnimation = beginAnimation
            ) {
                Text(
                    modifier = it.fillMaxWidth(),
                    text = stringResource(id = R.string.verify_email_address_instructions_2),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 14.sp,
                        lineHeight = 24.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(height = Dimens.marginMedium))
            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.END,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 5),
                beginAnimation = beginAnimation
            ) {
                ContractAIButton(
                    modifier = it,
                    text = stringResource(
                        id = R.string.continue_
                    ),
                    onClick = onNavigateToNextScreen,
                    enabled = !uiState.isLoading
                )
            }
            Spacer(modifier = Modifier.height(height = Dimens.marginMedium))
            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.END,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 5),
                beginAnimation = beginAnimation
            ) {
                TextButton(
                    modifier = it.fillMaxWidth(),
                    onClick = onResendEmailLink,
                    enabled = !uiState.isLoading
                ) {
                    Text(
                        text = stringResource(id = R.string.re_send_email_link),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = uiState.isLoading,
            exit = fadeOut(),
            enter = fadeIn()
        ) {
            ContractAILoadingOverlay()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VerifyEmailScreenPreview() {
    ContractAITheme {
        VerifyEmailScreen(
            onNavigateBack = {},
            beginAnimation = true,
            onNavigateToNextScreen = {},
            onResendEmailLink = {},
            uiState = VerifyEmailUiState()
        )
    }
}