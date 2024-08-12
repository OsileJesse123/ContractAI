package com.jesse.contractai.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesse.contractai.R
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.ui.theme.Dimens

@Composable
fun ContractHistoryItem(
    modifier: Modifier = Modifier,
    @DrawableRes
    icon: Int,
    @StringRes
    iconDescription: Int,
    title: String,
    timeStamp: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Row(
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = iconDescription),
                tint = MaterialTheme.colorScheme.tertiary
            )
            Spacer(modifier = Modifier.width(width = Dimens.marginExtraSmall))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 12.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = timeStamp,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 11.sp, color = MaterialTheme.colorScheme.tertiary
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(height = 5.dp))
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
private fun ContractHistoryItemPreview() {
    ContractAITheme {
        ContractHistoryItem(
            icon = R.drawable.ic_add_file,
            iconDescription = R.string.file_search_icon,
            title = "Employment Contract",
            timeStamp = "09 July, 2024",
            onClick = {}
        )
    }
}