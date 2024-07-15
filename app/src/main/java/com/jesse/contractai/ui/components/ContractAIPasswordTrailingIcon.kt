package com.jesse.contractai.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jesse.contractai.R
import com.jesse.contractai.ui.theme.ContractAITheme

@Composable
fun ContractAIPasswordTrailingIcon(
    modifier: Modifier = Modifier,
    isPasswordVisible: Boolean = true,
    onIconButtonClicked: (Boolean) -> Unit = {}
) {
    IconButton(
        modifier = modifier,
        onClick = {
            onIconButtonClicked(!isPasswordVisible)
        }
    ) {
        Icon(
            painter = painterResource(
                id = if (isPasswordVisible){
                    R.drawable.ic_outline_visibility_off
                } else{
                    R.drawable.ic_outline_visibility
                }
            ),
        contentDescription = stringResource(
                id = if (isPasswordVisible){
                    R.string.visibility_off_icon
                } else{
                    R.string.visibility_on_icon
                }
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContractAIPasswordTrailingIconPreview() {
    ContractAITheme {
        ContractAIPasswordTrailingIcon(

        )
    }
}

