package com.jesse.contractai.ui.screens.sign_up

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesse.contractai.R
import com.jesse.contractai.ui.animations.ContractAISlideInAnimation
import com.jesse.contractai.ui.animations.SlideDirection
import com.jesse.contractai.ui.components.ContractAIButton
import com.jesse.contractai.ui.components.ContractAICheckBox
import com.jesse.contractai.ui.components.ContractAIErrorText
import com.jesse.contractai.ui.components.ContractAIGoogleSignInButton
import com.jesse.contractai.ui.components.ContractAIOutlinedTextField
import com.jesse.contractai.ui.components.ContractAIPasswordTrailingIcon
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
    onNavigateToSignIn: () -> Unit
) {
    SignUpScreen(
        modifier = modifier,
        onSignInWithGoogle = {},
        onNavigateToSignIn = onNavigateToSignIn
    )
}

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onSignInWithGoogle: () -> Unit,
    onNavigateToSignIn: () -> Unit
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
            animationSpec = tween(durationMillis = 500)
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
            offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 1)
        ) {
            ContractAIGoogleSignInButton(
                modifier = it,
                onClick = onSignInWithGoogle,
                title = stringResource(id = R.string.sign_up_with_google),
                //isEnabled = !uiState.isLoading
            )
        }

        ContractAISlideInAnimation(
            modifier = Modifier,
            direction = SlideDirection.END,
            animationSpec = animationSpec,
            offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 2)
        ) {
            Divider(
                modifier = it.padding(vertical = 20.dp)
            )
        }

        ContractAISlideInAnimation(
            modifier = Modifier,
            direction = SlideDirection.END,
            animationSpec = animationSpec,
            offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 3)
        ) {
            ContractAIOutlinedTextField(
                modifier = it,
                value = "",
                isError = false,
                supportingText = {
                    ContractAIErrorText(
                        text = ""
                    )
                },
                onValueChange = {  },
                hint = stringResource(id = R.string.first_name),
                keyboardType = KeyboardType.Text
            )
        }

        ContractAISlideInAnimation(
            modifier = Modifier,
            direction = SlideDirection.END,
            animationSpec = animationSpec,
            offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 4)
        ) {
            ContractAIOutlinedTextField(
                modifier = it,
                value = "",
                isError = false,
                supportingText = {
                    ContractAIErrorText(
                        text = ""
                    )
                },
                onValueChange = {  },
                hint = stringResource(id = R.string.last_name),
                keyboardType = KeyboardType.Text
            )
        }

        ContractAISlideInAnimation(
            modifier = Modifier,
            direction = SlideDirection.END,
            animationSpec = animationSpec,
            offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 5)
        ) {
            ContractAIOutlinedTextField(
                modifier = it,
                value = "",
                isError = false,
                supportingText = {
                    ContractAIErrorText(
                        text = ""
                    )
                },
                onValueChange = { },
                hint = stringResource(id = R.string.enter_your_email_address),
                keyboardType = KeyboardType.Email
            )
        }

        ContractAISlideInAnimation(
            modifier = Modifier,
            direction = SlideDirection.END,
            animationSpec = animationSpec,
            offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 6)
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
                    value = "",
                    isError = false,
                    hint = stringResource(id = R.string.enter_your_password),
                    supportingText = {
                        AnimatedContent(targetState = false) {
                            isError ->
                            if(isError){
                                ContractAIErrorText(
                                    text = ""
                                )
                            } else{
                                ContractAIPasswordStrengthComposable(passwordStrength = PasswordStrength.WEAK)
                            }
                        }
                    },
                    trailingIcon = {
                        ContractAIPasswordTrailingIcon(
                            isPasswordVisible = false,
                            onIconButtonClicked = {
                                    isPasswordVisible ->
                            }
                        )
                    },
                    offsetAnimationDelay = 1000,
                    onValueChange = { },
                    keyboardType = KeyboardType.Password,
                    visualTransformation = if(true) VisualTransformation.None else PasswordVisualTransformation()
                )
            }
        }

        ContractAISlideInAnimation(
            modifier = Modifier,
            direction = SlideDirection.END,
            animationSpec = animationSpec,
            offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 7)
        ) {
            ContractAIOutlinedTextField(
                modifier = it,
                value = "",
                isError = false,
                hint = stringResource(id = R.string.confirm_password),
                supportingText = {
                    ContractAIErrorText(
                        text = ""
                    )
                },
                trailingIcon = {
                    ContractAIPasswordTrailingIcon(
                        isPasswordVisible = true,
                        onIconButtonClicked = {
                                isPasswordVisible ->
                        }
                    )
                },
                offsetAnimationDelay = 1000,
                onValueChange = { },
                keyboardType = KeyboardType.Password,
                visualTransformation = if(true) VisualTransformation.None else PasswordVisualTransformation()
            )
        }

        ContractAISlideInAnimation(
            modifier = Modifier,
            direction = SlideDirection.END,
            animationSpec = animationSpec,
            offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 8)
        ) {
            Row(
                modifier = it,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ContractAICheckBox(
                    modifier = Modifier
                        .size(17.dp),
                    checked = true,
                    onCheckedChange = {
                            readTermsAndConditions ->

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
            offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 9)
        ) {
            ContractAIButton(
                modifier = it.padding(top = Dimens.marginMedium),
                text = stringResource(
                    id = R.string.sign_in
                ),
                onClick = { },
                enabled = true
            )
        }

        ContractAISlideInAnimation(
            modifier = Modifier,
            direction = SlideDirection.END,
            animationSpec = animationSpec,
            offsetAnimationDelay = animationDelayTime + (animationDelayTimeDifference * 10)
        ) {
            Row(
                modifier = it.fillMaxWidth().padding(top = Dimens.marginMedium),
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
}


@Composable
fun ContractAIPasswordStrengthComposable(
    modifier: Modifier = Modifier,
    passwordStrength: PasswordStrength
) {
    val passwordStrengthStringId = when(passwordStrength){
        PasswordStrength.WEAK -> R.string.weak
        PasswordStrength.STRONG -> R.string.strong
        PasswordStrength.VERY_STRONG -> R.string.very_strong
    }
    val passwordStrengthColor = when(passwordStrength){
        PasswordStrength.WEAK -> MaterialTheme.colorScheme.error
        PasswordStrength.STRONG -> Green600
        PasswordStrength.VERY_STRONG -> Green900
    }
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
        Text(
            text = stringResource(
            id = passwordStrengthStringId),
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 11.sp,
                color = passwordStrengthColor
            )
        )

        Spacer(modifier = Modifier.width(width = 3.dp))

        Divider(
            modifier = Modifier.width(width = dividerWidth),
            thickness = 4.dp,
            color = passwordStrengthColor
        )

        Divider(
            modifier = Modifier.width(width = dividerWidth),
            thickness = 4.dp,
            color = passwordStrengthColor
        )

        Divider(
            modifier = Modifier.width(width = dividerWidth),
            thickness = 4.dp,
            color = passwordStrengthColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    ContractAITheme {
        SignUpScreen(
            onSignInWithGoogle = {},
            onNavigateToSignIn = {}
        )
    }
}