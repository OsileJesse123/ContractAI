package com.jesse.contractai.ui.screens.sign_up

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesse.contractai.R
import com.jesse.contractai.ui.animations.ContractAISlideInAnimation
import com.jesse.contractai.ui.animations.SlideDirection
import com.jesse.contractai.ui.components.ContractAIButton
import com.jesse.contractai.ui.components.ContractAICheckBox
import com.jesse.contractai.ui.components.ContractAIErrorText
import com.jesse.contractai.ui.components.ContractAIGoogleSignInButton
import com.jesse.contractai.ui.components.ContractAILoadingOverlay
import com.jesse.contractai.ui.components.ContractAIOutlinedTextField
import com.jesse.contractai.ui.components.ContractAIPasswordTrailingIcon
import com.jesse.contractai.ui.theme.Black800
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.ui.theme.Dimens
import com.jesse.contractai.ui.theme.Green600
import com.jesse.contractai.ui.theme.Green900
import com.jesse.contractai.util.animationDelayTime
import com.jesse.contractai.util.animationDelayTimeDifference
import com.jesse.contractai.util.animationSpec

@Composable
fun SignUpScreenRoute(
    modifier: Modifier = Modifier,
    onNavigateToSignIn: () -> Unit,
    onNavigateToVerifyEmail: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.signUpUiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = uiState.navigateToNextScreen) {
        if(uiState.navigateToNextScreen){
            onNavigateToVerifyEmail()
            viewModel.onNavigateToNextScreen()
        }
    }

    SignUpScreen(
        modifier = modifier,
        onSignUp = {
           viewModel.signUp()
        },
        onSignInWithGoogle = {
           viewModel.signUpWithGoogle()
        },
        onFirstNameChanged = {
            firstName ->
            viewModel.validateFirstName(firstName)
        },
        onLastNameChanged = {
            lastName ->
            viewModel.validateLastName(lastName)
        },
        onEmailChanged = {
             email ->
             viewModel.validateEmail(email)
        },
        onPasswordChanged = {
             password ->
             viewModel.validatePasswordStrength(password)
        },
        onConfirmPasswordChanged = {
             confirmPassword ->
             viewModel.validateConfirmPassword(confirmPassword)
        },
        togglePasswordVisibility = {
            isPasswordVisible ->
            viewModel.togglePasswordVisibility(isPasswordVisible)
        },
        toggleConfirmPasswordVisibility = {
             isPasswordVisible ->
             viewModel.toggleConfirmPasswordVisibility(isPasswordVisible)
        },
        onTermsAndConditionsChecked = {
            termsAndConditionsChecked ->
            viewModel.toggleTermsAndConditions(termsAndConditionsChecked)
        },
        onNavigateToSignIn = onNavigateToSignIn,
        uiState = uiState,
        beginAnimation = false
    )
}

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onSignUp: () -> Unit,
    onSignInWithGoogle: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    togglePasswordVisibility: (Boolean) -> Unit,
    toggleConfirmPasswordVisibility: (Boolean) -> Unit,
    onTermsAndConditionsChecked: (Boolean) -> Unit,
    beginAnimation: Boolean,
    uiState: SignUpUiState
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    start = Dimens.screenHorizontalPadding,
                    end = Dimens.screenHorizontalPadding,
                    top = Dimens.screenVerticalPadding
                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {

            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.TOP,
                animationSpec = tween(durationMillis = 500),
                beginAnimation = beginAnimation
            ) {
                Text(
                    modifier = it,
                    text = stringResource(id = R.string.sign_up),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 32.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(height = 20.dp))

            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.END,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 1),
                beginAnimation = beginAnimation
            ) {
                ContractAIGoogleSignInButton(
                    modifier = it,
                    onClick = onSignInWithGoogle,
                    title = stringResource(id = R.string.sign_up_with_google),
                    isEnabled = !uiState.isLoading
                )
            }

            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.END,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 2),
                beginAnimation = beginAnimation
            ) {
                Divider(
                    modifier = it.padding(vertical = 20.dp)
                )
            }

            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.END,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 3),
                beginAnimation = beginAnimation
            ) {
                ContractAIOutlinedTextField(
                    modifier = it,
                    value = uiState.firstName,
                    isError = uiState.firstNameError != null,
                    supportingText = {
                        ContractAIErrorText(
                            text = uiState.firstNameError?.let { stringResource(id = it) }
                        )
                    },
                    onValueChange = {
                        firstName ->
                        onFirstNameChanged(firstName)
                    },
                    hint = stringResource(id = R.string.first_name),
                    keyboardType = KeyboardType.Text
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
                    value = uiState.lastName,
                    isError = uiState.lastNameError != null,
                    supportingText = {
                        ContractAIErrorText(
                            text = uiState.lastNameError?.let { stringResource(id = it) }
                        )
                    },
                    onValueChange = {
                         lastName ->
                         onLastNameChanged(lastName)
                    },
                    hint = stringResource(id = R.string.last_name),
                    keyboardType = KeyboardType.Text
                )
            }

            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.END,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 5),
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
                    onValueChange = {
                          email ->
                          onEmailChanged(email)
                    },
                    hint = stringResource(id = R.string.enter_your_email_address),
                    keyboardType = KeyboardType.Email
                )
            }

            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.END,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 6),
                beginAnimation = beginAnimation
            ) {
                Column(
                    modifier = it
                ) {
                    Text(
                        text = stringResource(id = R.string.password),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    )
                    ContractAIOutlinedTextField(
                        modifier = Modifier.padding(top = 5.dp),
                        value = uiState.password,
                        isError = uiState.passwordStrength == PasswordStrength.WEAK,
                        hint = stringResource(id = R.string.enter_your_password),
                        supportingText = {
                            ContractAIPasswordStrengthComposable(passwordStrength = uiState.passwordStrength)
                        },
                        trailingIcon = {
                            ContractAIPasswordTrailingIcon(
                                isPasswordVisible = uiState.isPasswordVisible,
                                onIconButtonClicked = {
                                        isPasswordVisible ->
                                    togglePasswordVisibility(isPasswordVisible)
                                }
                            )
                        },
                        offsetAnimationDelay = 1000,
                        onValueChange = {
                              password ->
                              onPasswordChanged(password)
                        },
                        keyboardType = KeyboardType.Password,
                        visualTransformation = if(uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                    )
                }
            }

            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.END,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 7),
                beginAnimation = beginAnimation
            ) {
                ContractAIOutlinedTextField(
                    modifier = it,
                    value = uiState.confirmPassword,
                    isError = uiState.confirmPasswordError != null,
                    hint = stringResource(id = R.string.confirm_password),
                    supportingText = {
                        ContractAIErrorText(
                            text = uiState.confirmPasswordError?.let { stringResource(id = it) }
                        )
                    },
                    trailingIcon = {
                        ContractAIPasswordTrailingIcon(
                            isPasswordVisible = uiState.isConfirmPasswordVisible,
                            onIconButtonClicked = {
                                    isPasswordVisible ->
                                toggleConfirmPasswordVisibility(isPasswordVisible)
                            }
                        )
                    },
                    offsetAnimationDelay = 1000,
                    onValueChange = {
                         confirmPassword ->
                         onConfirmPasswordChanged(confirmPassword)
                    },
                    keyboardType = KeyboardType.Password,
                    visualTransformation = if(uiState.isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )
            }

            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.END,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 8),
                beginAnimation = beginAnimation
            ) {
                Row(
                    modifier = it,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    ContractAICheckBox(
                        modifier = Modifier
                            .size(17.dp),
                        checked = uiState.termsAndConditionsChecked,
                        onCheckedChange = {
                                readTermsAndConditions ->
                                onTermsAndConditionsChecked(readTermsAndConditions)
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        modifier = Modifier.windowInsetsTopHeight(insets = WindowInsets(top = 21.dp)),
                        text = stringResource(id = R.string.terms_and_conditions),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 15.sp
                        ),
                    )
                }
            }

            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.END,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 9),
                beginAnimation = beginAnimation
            ) {
                ContractAIButton(
                    modifier = it.padding(top = Dimens.marginMedium),
                    text = stringResource(
                        id = R.string.sign_up
                    ),
                    onClick = onSignUp,
                    enabled = uiState.isFormValid() && !uiState.isLoading
                )
            }

            ContractAISlideInAnimation(
                modifier = Modifier,
                direction = SlideDirection.END,
                animationSpec = animationSpec,
                offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 10),
                beginAnimation = beginAnimation
            ) {
                Row(
                    modifier = it
                        .fillMaxWidth()
                        .padding(top = Dimens.marginMedium),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.have_an_account),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        modifier = Modifier.clickable {
                            onNavigateToSignIn()
                        },
                        text = stringResource(id = R.string.sign_in),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.primary
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


@Composable
fun ContractAIPasswordStrengthComposable(
    modifier: Modifier = Modifier,
    passwordStrength: PasswordStrength
) {
    val passwordStrengthStringId: Int? = when(passwordStrength){
        PasswordStrength.WEAK -> R.string.weak
        PasswordStrength.STRONG -> R.string.strong
        PasswordStrength.VERY_STRONG -> R.string.very_strong
        PasswordStrength.NONE -> null
    }
    val passwordStrengthColor by animateColorAsState(
        targetValue = when(passwordStrength){
            PasswordStrength.WEAK -> MaterialTheme.colorScheme.error
            PasswordStrength.STRONG -> Green600
            PasswordStrength.VERY_STRONG -> Green900
            PasswordStrength.NONE -> Black800.copy(alpha = .1f)
        },
        tween(300),
        label = "password strength color animation"
    )
    val dividerWidth = 20.dp
    Row(
        modifier = modifier
            .wrapContentSize()
            // The purpose of this padding is to give decent spacing between the composable using this
            // and the composable beneath it
            .padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        AnimatedContent(
            targetState = passwordStrengthStringId,
            transitionSpec = {
                (fadeIn(animationSpec = tween(300)) + scaleIn())
                    .togetherWith(
                    fadeOut(animationSpec = tween(300)) + scaleOut()
                )
            }
        ) {
            targetState ->
            Text(
                text = targetState?.let {
                    stringResource(
                        id = it
                    )
                } ?: "",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 11.sp,
                    color = passwordStrengthColor
                )
            )
        }

        Spacer(modifier = Modifier.width(width = 3.dp))

        Divider(
            modifier = Modifier.width(width = dividerWidth),
            thickness = 4.dp,
            color = passwordStrengthColor
        )

        Divider(
            modifier = Modifier.width(width = dividerWidth),
            thickness = 4.dp,
            color = if (passwordStrength == PasswordStrength.WEAK) Black800.copy(alpha = .1f) else passwordStrengthColor
        )

        Divider(
            modifier = Modifier.width(width = dividerWidth),
            thickness = 4.dp,
            color = if(passwordStrength == PasswordStrength.WEAK || passwordStrength == PasswordStrength.STRONG) Black800.copy(alpha = .1f) else passwordStrengthColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    ContractAITheme {
        SignUpScreen(
            onSignInWithGoogle = {},
            onNavigateToSignIn = {},
            uiState = SignUpUiState(),
            onEmailChanged = {},
            onPasswordChanged = {},
            onTermsAndConditionsChecked = {},
            onLastNameChanged = {},
            onFirstNameChanged = {},
            onSignUp = {},
            onConfirmPasswordChanged = {},
            toggleConfirmPasswordVisibility = {},
            togglePasswordVisibility = {},
            beginAnimation = true
        )
    }
}