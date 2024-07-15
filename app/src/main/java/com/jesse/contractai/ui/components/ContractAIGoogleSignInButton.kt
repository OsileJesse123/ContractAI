package com.jesse.contractai.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesse.contractai.R
import com.jesse.contractai.ui.theme.ContractAITheme

@Composable
fun ContractAIGoogleSignInButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
    title: String
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = MaterialTheme.shapes.extraSmall,
        enabled = isEnabled
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(
                    id = R.drawable.ic_google
                ),
                contentDescription = stringResource(id = R.string.google_icon)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContractAIGoogleSignInButtonPreview() {
    ContractAITheme {
        ContractAIGoogleSignInButton(
            title = "Continue with Google",
            onClick = {}
        )
    }
}