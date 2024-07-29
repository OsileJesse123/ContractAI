package com.jesse.contractai.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesse.contractai.R
import com.jesse.contractai.ui.components.ContractHistoryItem
import com.jesse.contractai.ui.theme.Blue600
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.ui.theme.Dimens
import com.jesse.contractai.ui.theme.Green100
import com.jesse.contractai.ui.theme.Green600
import com.jesse.contractai.ui.theme.HexagonShape
import com.jesse.contractai.ui.theme.Orange200
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    onNavigateToContractHistory: () -> Unit,
    onNavigateToLoginScreen: () -> Unit,
    updateBottomBarState: (Boolean) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        onDispose {
            updateBottomBarState(false)
        }
    }
    HomeScreen(
        uiState = uiState,
        onNavigateToContractHistory = onNavigateToContractHistory,
        onNavigateToLoginScreen = onNavigateToLoginScreen,
        updateBottomBarState = updateBottomBarState
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToContractHistory: () -> Unit,
    updateBottomBarState: (Boolean) -> Unit,
    onNavigateToLoginScreen: () -> Unit,
    uiState: HomeUiState
) {

    var showLogoutDialog by remember {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()
    
    val scrollState = rememberScrollState()

    var hintCardClicked1 by remember {
        mutableStateOf(false)
    }

    var hintCardClicked2 by remember {
        mutableStateOf(false)
    }

    if (showLogoutDialog){
        LogoutDialog(
            onDismiss = { showLogoutDialog = false },
            userName = uiState.userName,
            userEmail = uiState.userEmail,
            onLogout = onNavigateToLoginScreen
        )
    }


    LaunchedEffect(key1 = scrollState) {
        snapshotFlow { scrollState.value }.collect{
            value ->
            if(value == scrollState.maxValue && scrollState.canScrollBackward){
                updateBottomBarState(false)
            } else{
                updateBottomBarState(true)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(
                horizontal = Dimens.screenHorizontalPadding,
                vertical = Dimens.screenVerticalPadding
            ),
        verticalArrangement = Arrangement.spacedBy(Dimens.marginSmall)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(
                            alpha = .1f
                        )
                    )
                    .padding(12.dp),
                painter = painterResource(
                    id = R.drawable.ic_contract_ai_burger
                ),

                contentDescription = stringResource(id = R.string.double_back_arrow)
            )
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        showLogoutDialog = true
                    },
                painter = painterResource(id = R.drawable.ai_welcome_img),
                contentDescription = stringResource(id = R.string.ai_welcome_image)
            )
        }

        Text(
            text = stringResource(id = R.string.welcome_message, uiState.userName),
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 26.sp
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // ContractHintCard 1
            ContractHintCard(
                gradientStartColor = Blue600,
                gradientEndColor = Green100,
                hexagonBackgroundColor = MaterialTheme.colorScheme.primary,
                hint = R.string.review_any_kind_of_contract,
                description = R.string.review_contract_hint,
                cardTitle = R.string.review_contract,
                clicked = hintCardClicked1,
                hexagonIcon = R.drawable.ic_file_search,
                onClicked = {
                    coroutineScope.launch {
                        hintCardClicked1 = true
                        delay(5_000)
                        hintCardClicked1 = false
                    }
                }
            )
            // ContractHintCard 2
            ContractHintCard(
                gradientStartColor = Green100,
                gradientEndColor = Orange200,
                hexagonBackgroundColor = Green600,
                hint = R.string.draft_any_kind_of_contract,
                description = R.string.draft_contract_hint,
                cardTitle = R.string.draft_contract,
                clicked = hintCardClicked2,
                hexagonIcon = R.drawable.ic_add_file,
                onClicked = {
                    coroutineScope.launch {
                        hintCardClicked2 = true
                        delay(5_000)
                        hintCardClicked2 = false
                    }
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.contract_history),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.sp
                )
            )
            if (uiState.contractHistory.isNotEmpty()){
                Text(
                    modifier = Modifier
                        .clickable {
                            onNavigateToContractHistory()
                        },
                    text = stringResource(id = R.string.see_all),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 12.sp, color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

        if (uiState.contractHistory.isNotEmpty()){
            uiState.contractHistory.forEach {
                contractResult ->
                ContractHistoryItem(
                    icon = contractResult.getContractResultIcon(),
                    iconDescription = contractResult.getContractResultIconDescription(),
                    title = contractResult.title,
                    timeStamp = contractResult.timeStamp
                )
            }
        }
        if(uiState.contractHistory.isEmpty() && !uiState.isLoading){
            EmptyContractHistory(modifier = Modifier.padding(top = Dimens.marginSmall))
        }
        if(uiState.contractHistory.isEmpty() && uiState.isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(modifier = Modifier.height(height = Dimens.marginMedium))
    }
}

@Composable
fun EmptyContractHistory(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.lady_reading_illustration),
            contentDescription = stringResource(id = R.string.lady_reading_illustration)
        )
        Spacer(modifier = Modifier.height(height = Dimens.marginExtraSmall))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.its_quiet_here),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 20.sp
            ),
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.you_will_find_your_recent_contacts_here),
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 14.sp
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LogoutDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    userName: String,
    userEmail: String,
    onLogout: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = modifier
                .width(300.dp)
                .height(330.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = onDismiss
                ) {
                    Icon(
                        modifier = modifier,
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = stringResource(id = R.string.cancel_icon),
                    )
                }
                Box(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Image(
                        modifier = Modifier.size(75.dp),
                        painter = painterResource(id = R.drawable.ai_welcome_img),
                        contentDescription = stringResource(id = R.string.ai_welcome_image)
                    )
                    Icon(
                        modifier = Modifier
                            .clip(CircleShape)
                            .align(Alignment.BottomEnd)
                            .background(color = MaterialTheme.colorScheme.onSecondary)
                            .padding(5.dp)
                            .wrapContentSize(),
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = stringResource(id = R.string.edit_icon),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                Spacer(modifier = Modifier.height(height = 10.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.hi_user, userName),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = userEmail,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(height = 10.dp))
                OutlinedButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.error.copy(
                            alpha = .1f
                        ),
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                    onClick = onLogout,
                ) {
                    Text(
                        text = stringResource(id = R.string.logout_of_your_account),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}

@Composable
fun ContractHintCard(
    modifier: Modifier = Modifier,
    gradientStartColor: Color,
    gradientEndColor: Color,
    hexagonBackgroundColor: Color,
    clicked: Boolean = false,
    onClicked: () -> Unit,
    @StringRes
    cardTitle: Int,
    @StringRes
    hint: Int,
    @StringRes
    description: Int,
    @DrawableRes
    hexagonIcon: Int
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(gradientStartColor, gradientEndColor),
    )
    val boxTwoWeight by animateFloatAsState(
        targetValue = if (clicked) 2f else 1f,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing),
        label = "contact hint card weight animation"
    )
    val boxTwoText = if (clicked) description else hint

    Card(
        modifier = modifier
            .width(150.dp)
            .height(160.dp)
            .clickable {
                onClicked()
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = MaterialTheme.shapes.small
    ) {
        Column(modifier = Modifier) {
            // Box 1
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ){
                Image(
                    modifier = Modifier
                        .drawWithContent {
                            drawContent()
                            drawRect(gradient, blendMode = BlendMode.Modulate)
                        }
                        .alpha(.1f),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.card_background_image),
                    contentDescription = stringResource(id = R.string.contract_card_hint_bg_img)
                )
                this@Column.AnimatedVisibility(
                    visible = clicked,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = cardTitle),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 12.sp
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(30.dp)
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(constraints)
                                layout(placeable.width, placeable.height) {
                                    placeable.place(0, placeable.height / 2)
                                }
                            }
                            .drawWithContent {
                                drawContent()
                                drawPath(
                                    path = HexagonShape.drawCustomHexagonPath(size),
                                    color = Color.White,
                                    style = Stroke(
                                        width = 3.5f,
                                        pathEffect = PathEffect.cornerPathEffect(5f)
                                    )
                                )
                            }
                            .background(
                                color = hexagonBackgroundColor,
                                shape = HexagonShape()
                            )
                    ){
                        Image(
                            modifier = Modifier.size(13.dp),
                            painter = painterResource(id = hexagonIcon),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(color = Color.White)
                        )
                    }
                }
            }
            // Box 2
            Box(
                modifier = Modifier
                    .weight(boxTwoWeight)
                    .padding(horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = boxTwoText,
                    transitionSpec = {
                        (fadeIn(animationSpec = tween(300)) + scaleIn())
                            .togetherWith(
                                fadeOut(animationSpec = tween(300)) + scaleOut()
                            )
                    },
                    label = "contact hint card box 2 text animation"
                ) {
                    targetState ->
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = targetState),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = if (clicked) 10.sp else 13.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ContractAITheme {
        HomeScreen(
            uiState = HomeUiState(userName = "Iriekefe", isLoading = true),
            onNavigateToLoginScreen = {},
            onNavigateToContractHistory = {},
            updateBottomBarState = {}
        )
    }
}

