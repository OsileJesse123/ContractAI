package com.jesse.contractai.ui.screens.sign_in

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesse.contractai.R
import com.jesse.contractai.ui.animations.SlideDirection
import com.jesse.contractai.ui.animations.ContractAISlideInAnimation
import com.jesse.contractai.ui.components.ContractAIButton
import com.jesse.contractai.ui.components.ContractAICheckBox
import com.jesse.contractai.ui.components.ContractAIErrorText
import com.jesse.contractai.ui.components.ContractAIGoogleSignInButton
import com.jesse.contractai.ui.components.ContractAILoadingOverlay
import com.jesse.contractai.ui.components.ContractAIOutlinedTextField
import com.jesse.contractai.ui.components.ContractAIPasswordTrailingIcon
import com.jesse.contractai.ui.components.ContractAISnackBar
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.ui.theme.Dimens
import com.jesse.contractai.util.animationDelayTime
import com.jesse.contractai.util.animationDelayTimeDifference
import com.jesse.contractai.util.animationSpec
import kotlinx.coroutines.launch

@Composable
fun SignInScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToHome:() -> Unit,
    onNavigateToVerifyEmail: () -> Unit,
    onNavigateToSignUp:() -> Unit
) {
    val uiState by viewModel.signInUiState.collectAsStateWithLifecycle()
    val googleSignInLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            viewModel.signInWithGoogle(result.data)
        }

    LaunchedEffect(key1 = uiState.nextDestination) {
        when(uiState.nextDestination){
            NextDestination.HOME -> {
                onNavigateToHome()
                viewModel.onNavigateToNextScreen()
            }
            NextDestination.VERIFY_EMAIL -> {
                onNavigateToVerifyEmail()
                viewModel.onNavigateToNextScreen()
            }
            else -> {
                // Do nothing
            }
        }
    }

    SignInScreen(
        modifier = modifier,
        onSignInWithGoogle = {googleSignInLauncher.launch(viewModel.getSignInIntent())},
        onSignIn = {viewModel.signIn()},
        uiState = uiState,
        onNavigateToForgotPassword = onNavigateToForgotPassword,
        onEmailChanged = {
            email -> viewModel.validateEmail(email)
        },
        onPasswordChanged = {
            password ->
            viewModel.validatePassword(password)
        },
        togglePasswordVisibility = {
            isPasswordVisible ->
            viewModel.togglePasswordVisibility(isPasswordVisible)
        },
        onRememberPassword = {
            rememberPassword ->
            viewModel.rememberPassword(rememberPassword)
        },
        onNavigateToSignUp = onNavigateToSignUp,
        onSnackBarShown = { viewModel.onErrorMessageShown() },
        beginAnimation = false
    )
}

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onSignInWithGoogle: () -> Unit,
    onSignIn: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    togglePasswordVisibility:(Boolean) -> Unit,
    onRememberPassword: (Boolean) -> Unit,
    onNavigateToSignUp:() -> Unit,
    onSnackBarShown: () -> Unit,
    beginAnimation: Boolean,
    uiState: SignInUiState
) {
    
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = Dimens.screenHorizontalPadding,
                        end = Dimens.screenHorizontalPadding,
                        top = Dimens.screenVerticalPadding
                    )
            ) {

                val (
                    screenTitle,
                    googleSignInButton,
                    divider,
                    emailTextField,
                    passwordTextField,
                    forgotPasswordButton,
                    rememberPasswordCheck,
                    signInButton,
                    signUpContent
                ) = createRefs()

                val topGuideLine = createGuidelineFromTop(.18f)

                ContractAISlideInAnimation(
                    modifier = Modifier,
                    direction = SlideDirection.TOP,
                    animationSpec = tween(durationMillis = 500),
                    beginAnimation = beginAnimation
                ) {
                    Text(
                        modifier = it.constrainAs(screenTitle) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                        text = stringResource(id = R.string.sign_in),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 32.sp
                        )
                    )
                }

                ContractAISlideInAnimation(
                    modifier = Modifier,
                    direction = SlideDirection.END,
                    animationSpec = animationSpec,
                    offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 1),
                    beginAnimation = beginAnimation
                ) {
                    ContractAIGoogleSignInButton(
                        modifier = it.constrainAs(googleSignInButton) {
                            top.linkTo(topGuideLine)
                        },
                        onClick = onSignInWithGoogle,
                        title = stringResource(id = R.string.continue_with_google),
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
                        modifier = it.constrainAs(divider) {
                            top.linkTo(googleSignInButton.bottom, margin = Dimens.marginMedium)
                        }
                    )
                }

                // Email TextField
                ContractAISlideInAnimation(
                    modifier = Modifier,
                    direction = SlideDirection.END,
                    animationSpec = animationSpec,
                    offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 3),
                    beginAnimation = beginAnimation
                ) {
                    ContractAIOutlinedTextField(
                        modifier = it
                            .constrainAs(emailTextField) {
                                top.linkTo(divider.bottom, margin = Dimens.marginMedium)
                            }
                        ,
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

                // Password TextField
                ContractAISlideInAnimation(
                    modifier = Modifier,
                    direction = SlideDirection.END,
                    animationSpec = animationSpec,
                    offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 4),
                    beginAnimation = beginAnimation
                ) {
                    ContractAIOutlinedTextField(
                        modifier = it
                            .constrainAs(passwordTextField) {
                                top.linkTo(emailTextField.bottom, margin = Dimens.marginMedium)
                            },
                        value = uiState.password,
                        isError = uiState.passwordError != null,
                        hint = stringResource(id = R.string.enter_your_password),
                        supportingText = {
                            ContractAIErrorText(
                                text = uiState.passwordError?.let { stringResource(id = it) }
                            )
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
                        onValueChange = onPasswordChanged,
                        keyboardType = KeyboardType.Password,
                        visualTransformation = if(uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                    )
                }

                ContractAISlideInAnimation(
                    modifier = Modifier,
                    direction = SlideDirection.END,
                    animationSpec = animationSpec,
                    offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 5),
                    beginAnimation = beginAnimation
                ) {
                    TextButton(
                        modifier = it
                            .constrainAs(forgotPasswordButton) {
                                top.linkTo(passwordTextField.bottom, margin = 10.dp)
                            },
                        onClick = onNavigateToForgotPassword,
                    ) {
                        Text(
                            text = stringResource(id = R.string.forgot_password),
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 14.sp
                            )
                        )
                    }
                }

                ContractAISlideInAnimation(
                    modifier = Modifier,
                    direction = SlideDirection.END,
                    animationSpec = animationSpec,
                    offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 6),
                    beginAnimation = beginAnimation
                ) {
                    Row(
                        modifier = it
                            .constrainAs(rememberPasswordCheck) {
                                top.linkTo(forgotPasswordButton.bottom, margin = 10.dp)
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        ContractAICheckBox(
                            modifier = Modifier
                                .size(18.dp),
                            checked = uiState.rememberPassword,
                            onCheckedChange = {
                                    rememberPassword ->
                                onRememberPassword(rememberPassword)
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.remember_password),
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 16.sp
                            ),
                            textAlign = TextAlign.Justify
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
                    ContractAIButton(
                        modifier = it
                            .constrainAs(signInButton) {
                                top.linkTo(rememberPasswordCheck.bottom, margin = 30.dp)
                                bottom.linkTo(parent.bottom)
                            },
                        text = stringResource(
                            id = R.string.sign_in
                        ),
                        onClick = onSignIn,
                        enabled = uiState.isFormValid() && !uiState.isLoading
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
                        modifier = it
                            .fillMaxWidth()
                            .constrainAs(signUpContent) {
                                top.linkTo(signInButton.bottom, margin = 30.dp)
                                bottom.linkTo(parent.bottom, margin = 30.dp)
                            },
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.dont_have_an_account),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            modifier = Modifier.clickable {
                                onNavigateToSignUp()
                            },
                            text = stringResource(id = R.string.sign_up),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )

                    }
                }
            }
        }

        ContractAISnackBar(
            modifier = Modifier,
            message = uiState.errorMessage,
            onSnackBarShown = onSnackBarShown
        )

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
private fun SignInScreenPreview() {
    ContractAITheme {
        SignInScreen(
            uiState = SignInUiState(),
            onSignIn = {},
            onNavigateToForgotPassword = {},
            onEmailChanged = {},
            onPasswordChanged = {},
            togglePasswordVisibility = {},
            onRememberPassword = {},
            onSignInWithGoogle = {},
            onNavigateToSignUp = {},
            beginAnimation = true,
            onSnackBarShown = {}
        )
    }
}