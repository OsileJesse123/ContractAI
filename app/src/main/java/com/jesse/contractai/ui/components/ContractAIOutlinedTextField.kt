package com.jesse.contractai.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jesse.contractai.ui.theme.ContractAITheme

@Composable
fun ContractAIOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    isError: Boolean = false,
    offsetAnimationDelay: Long = 500,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    supportingText: @Composable (() -> Unit)? = null
) {

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.tertiary
                )
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            errorIndicatorColor = MaterialTheme.colorScheme.error,
            errorContainerColor = MaterialTheme.colorScheme.background
        ),
        textStyle = MaterialTheme.typography.titleMedium,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        supportingText = supportingText,
        isError = isError
    )
}

@Preview(showBackground = true)
@Composable
private fun ContractAIOutlinedTextFieldPreview() {
    ContractAITheme {
        ContractAIOutlinedTextField(
            value = "",
            hint = "Enter email address",
            keyboardType = KeyboardType.Password,
            onValueChange = {}
        )
    }
}