package com.jesse.contractai.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesse.contractai.R
import com.jesse.contractai.ui.theme.ContractAITheme

@Composable
fun ContractAICheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(20.dp)
            .clip(RectangleShape)
            .clickable { onCheckedChange(!checked) }
            .border(width = 1.dp, color = Color.Black)

    ) {
        if (checked) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_check),
                contentDescription = stringResource(id = R.string.check_mark_icon),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContractAICheckBoxPreview() {
    ContractAITheme {
        ContractAICheckBox(checked = true) {

        }
    }
}