package com.jesse.contractai.ui.screens.forgot_password

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesse.contractai.R
import com.jesse.contractai.ui.animations.ContractAISlideInAnimation
import com.jesse.contractai.ui.animations.SlideDirection
import com.jesse.contractai.ui.components.ContractAIButton
import com.jesse.contractai.ui.components.ContractAIErrorText
import com.jesse.contractai.ui.components.ContractAILoadingOverlay
import com.jesse.contractai.ui.components.ContractAIOutlinedTextField
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.ui.theme.Dimens
import com.jesse.contractai.util.animationDelayTime
import com.jesse.contractai.util.animationDelayTimeDifference
import com.jesse.contractai.util.animationSpec

@Composable
fun ForgotPasswordRoute(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val uiState by viewModel.forgotPasswordUiState.collectAsStateWithLifecycle()

    ForgotPasswordScreen(
        modifier = modifier,
        onNavigateBack = onNavigateBack,
        beginAnimation = false,
        uiState = uiState,
        onEmailChanged = {
            email ->
            viewModel.validateEmail(email)
        },
        onResetPassword = {
            viewModel.reset()
        }
    )
}

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onResetPassword: () -> Unit,
    beginAnimation: Boolean,
    uiState: ForgotPasswordUiState
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = Dimens.screenHorizontalPadding,
                    end = Dimens.screenHorizontalPadding,
                    top = Dimens.screenVerticalPadding
                )
        ){
            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.TOP,
                animationSpec = tween(durationMillis = 500),
                beginAnimation = beginAnimation
            ) {
                IconButton(
                    modifier = it
                        .clip(CircleShape)
                    ,
                    onClick = onNavigateBack,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(
                            alpha = .1f
                        )
                    )
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_double_back_arrow
                        ),
                        tint = MaterialTheme.colorScheme.tertiary,
                        contentDescription = stringResource(id = R.string.double_back_arrow)
                    )
                }
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
                    text = stringResource(id = R.string.forgot_password_title),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 18.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(height = 50.dp))
            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.END,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 2),
                beginAnimation = beginAnimation
            ) {
                Text(
                    modifier = it.fillMaxWidth(),
                    text = stringResource(id = R.string.forgot_your_password),
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
                    text = stringResource(id = R.string.reset_password_instructions_text),
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
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 4),
                beginAnimation = beginAnimation
            ) {
                Text(
                    modifier = it,
                    text = stringResource(id = R.string.password),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                )
            }
            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.END,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 4),
                beginAnimation = beginAnimation
            ) {
                ContractAIOutlinedTextField(
                    modifier = it,
                    value = uiState.email,
                    isError = uiState.emailError != null,
                    supportingText = {
                        ContractAIErrorText(
                            text = uiState.emailError?.let { stringResource(id = it) }
                        )
                    },
                    onValueChange = onEmailChanged,
                    hint = stringResource(id = R.string.enter_your_email_address),
                    keyboardType = KeyboardType.Email
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
                        id = R.string.reset
                    ),
                    onClick = onResetPassword,
                    enabled = uiState.isFormValid() && !uiState.isLoading
                )
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
private fun ForgotPasswordScreenPreview() {
    ContractAITheme {
        ForgotPasswordScreen(
            onNavigateBack = {},
            beginAnimation = true,
            onEmailChanged = {},
            onResetPassword = {},
            uiState = ForgotPasswordUiState()
        )
    }
}