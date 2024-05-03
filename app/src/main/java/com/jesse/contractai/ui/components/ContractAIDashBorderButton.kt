package com.jesse.contractai.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesse.contractai.R
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.util.dashedBorder

@Composable
fun ContractAIDashBorderButton(
    modifier: Modifier = Modifier,
    contentColor: Color,
    onClick: () -> Unit,
    buttonText: String,
    @DrawableRes drawableIcon: Int
) {

    Button(
        modifier = modifier
            .fillMaxWidth()
            .dashedBorder(
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.small
            ),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary.copy(
                alpha = .22f
            ),
            contentColor = contentColor
        ),
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues(vertical = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.sp
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(painter = painterResource(
                id = drawableIcon),
                contentDescription = stringResource(id = R.string.file_search_icon))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContractAIDashBorderButtonPreview() {
    ContractAITheme {
        ContractAIDashBorderButton(
            contentColor = MaterialTheme.colorScheme.error,
            onClick = {},
            buttonText = "Review my Contract",
            drawableIcon = R.drawable.ic_file_search
        )
    }
}