package com.jesse.contractai.ui.screens.contract_nature

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesse.contractai.R
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.util.dashedBorder

@Composable
fun ContractNatureRoute(
    onNavigateBack: () -> Unit,
    onContinue: (String) -> Unit
) {
    ContractNatureScreen(
        onNavigateBack = onNavigateBack,
        onContinue = onContinue
    )
}

@Composable
fun ContractNatureScreen(
    onNavigateBack: () -> Unit,
    onContinue: (String) -> Unit
) {
    var answer by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 30.dp)

    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .weight(2f),
        ) {
            Text(
                text = stringResource(id = R.string.what_is_the_nature_of_your_contract),
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 21.sp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .dashedBorder(
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = MaterialTheme.shapes.small
                    ),
                value = answer,
                textStyle = MaterialTheme.typography.titleMedium,
                onValueChange = {
                     answer = it
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.type_your_answer_here),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSecondary.copy(
                                alpha = .5f
                            )
                        )
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.tertiary.copy(
                        alpha = .1f
                    ),
                    unfocusedContainerColor = MaterialTheme.colorScheme.tertiary.copy(
                        alpha = .1f
                    ),
                    focusedIndicatorColor = Color.Transparent, // Specify the focused indicator color
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.border(width = 1.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = CircleShape
                )
            ) {
                Icon(painter = painterResource(
                    id = R.drawable.ic_arrow_back),
                    contentDescription = stringResource(id = R.string.arrow_back_icon),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
            ElevatedButton(
                onClick = {
                    if (answer.isNotEmpty()){
                        onContinue(answer)
                    }
                },
                shape = MaterialTheme.shapes.small,
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 5.dp),
                contentPadding = PaddingValues(horizontal = 40.dp, vertical = 12.dp),
                enabled = answer.isNotEmpty(),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.continue_),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContractNatureScreenPreview() {
    ContractAITheme {
        ContractNatureScreen(
            onNavigateBack = {},
            onContinue = {}
        )
    }
}