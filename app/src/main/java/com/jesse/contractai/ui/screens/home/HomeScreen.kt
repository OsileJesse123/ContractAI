package com.jesse.contractai.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesse.contractai.R
import com.jesse.contractai.ui.components.ContractAIDashBorderButton
import com.jesse.contractai.ui.components.ContractAISnackBar
import com.jesse.contractai.ui.theme.ContractAITheme
import kotlinx.coroutines.delay

@Composable
fun HomeRoute(
    userName: String,
    onNavigateToCreateContract: () -> Unit
) {

    HomeScreen(
        userName = userName,
        onNavigateToCreateContract
    )
}

@Composable
fun HomeScreen(
    userName: String,
    onNavigateToCreateContract: () -> Unit
) {
    var showSnackBar by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(showSnackBar){
        if (showSnackBar){
            delay(1_000L)
            showSnackBar = false
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 30.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column {
                    Text(
                        text = stringResource(id = R.string.hello),
                        style = MaterialTheme.typography.titleSmall
                            .copy(fontSize = 14.sp, color = MaterialTheme.colorScheme.tertiary)
                    )
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 20.sp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ai_welcome_img),
                    contentDescription = stringResource(id = R.string.ai_welcome_image))
            }
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.how_can_i_assist),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 26.sp
                ),
            )
            Spacer(modifier = Modifier.height(200.dp))
            ContractAIDashBorderButton(
                contentColor = MaterialTheme.colorScheme.primary,
                onClick = onNavigateToCreateContract,
                buttonText = stringResource(id = R.string.create_a_contract),
                drawableIcon = R.drawable.ic_add_file
            )
            Spacer(modifier = Modifier.height(30.dp))
            ContractAIDashBorderButton(
                contentColor = MaterialTheme.colorScheme.error,
                onClick = {
                    showSnackBar = true
                },
                buttonText = stringResource(id = R.string.create_a_contract),
                drawableIcon = R.drawable.ic_file_search
            )
        }
        AnimatedVisibility(visible = showSnackBar) {
            ContractAISnackBar(
                modifier = Modifier.padding(bottom = 50.dp, start = 20.dp, end = 20.dp),
                cardColor = MaterialTheme.colorScheme.primary,
                message = stringResource(id = R.string.coming_soon)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ContractAITheme {
        HomeScreen(
            userName = "Jesse",
            onNavigateToCreateContract = {}
        )
    }
}

