package com.jesse.contractai.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jesse.contractai.R
import com.jesse.contractai.ui.theme.ContractAITheme

@Composable
fun ContractAIOutlineCircularButton(
    modifier: Modifier = Modifier,
    color: Color,
    @DrawableRes
    icon: Int,
    @StringRes
    iconDescription: Int,
    iconSize: Dp = 25.dp,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.border(width = 2.dp,
            color = color,
            shape = CircleShape
        )
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            painter = painterResource(
                id = icon
            ),
            contentDescription = stringResource(id = iconDescription),
            tint = color
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun ContractAIOutlineCircularButtonPreview() {
    ContractAITheme {
        ContractAIOutlineCircularButton(
            color = MaterialTheme.colorScheme.error,
            icon = R.drawable.ic_arrow_back,
            iconDescription = R.string.arrow_back_icon,
            onClick = {}
        )
    }
}