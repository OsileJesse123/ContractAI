package com.jesse.contractai.ui.screens.conversation

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesse.contractai.R
import com.jesse.contractai.ui.components.ContractAIBackButton
import com.jesse.contractai.ui.components.ContractAICircularProgressIndicator
import com.jesse.contractai.ui.modifiers.dashedBorder
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.ui.theme.DarkBlue50
import com.jesse.contractai.ui.theme.Dimens
import com.jesse.contractai.ui.theme.Green600
import com.jesse.contractai.ui.theme.Orange300
import com.jesse.contractai.util.ConversationMessageStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ConversationRoute(
    modifier: Modifier = Modifier,
    reviewedContractTimeStamp: Long,
    viewModel: ConversationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val conversationMessages by viewModel.conversationMessages.collectAsStateWithLifecycle()
    val reviewedContract by viewModel.reviewedContract.collectAsStateWithLifecycle()

    ConversationScreen(
        screenTitle = reviewedContract?.title ?: "Conversation",
        onNavigateBack = onNavigateBack,
        conversationMessages = conversationMessages,
        onSend = {
            viewModel.sendMessage(
                message = it,
                reviewedContractTimeStamp = reviewedContractTimeStamp
            )
        },
        onRetry = {
            viewModel.sendMessage(
                message = it.content,
                reviewedContractTimeStamp = reviewedContractTimeStamp,
                timeStamp = it.timeStamp
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    screenTitle: String,
    conversationMessages: List<ConversationMessage>,
    onSend: (String) -> Unit,
    onRetry: (ConversationMessage) -> Unit
) {
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val showButton by remember {
        derivedStateOf {
            listState.canScrollBackward
        }
    }
    LaunchedEffect(conversationMessages) {
        listState.animateScrollToItem(0)
    }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehaviour.nestedScrollConnection)
            .background(color = MaterialTheme.colorScheme.background),
        topBar = {
            MediumTopAppBar(
                navigationIcon = {
                    ContractAIBackButton(
                        modifier = Modifier,
                        onClick = onNavigateBack
                    )
                },
                title = {
                    Text(
                        modifier = Modifier,
                        text = screenTitle,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 18.sp
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                scrollBehavior = scrollBehaviour
            )
        },
        bottomBar = {
            PromptAIBox(
                onSend = onSend
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = showButton,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                IconButton(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = .8f),
                        shape = CircleShape
                    ),
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_keyboard_double_arrow_down),
                        contentDescription = stringResource(id = R.string.double_down_arrow),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    ) {
        paddingValues ->
        if (conversationMessages.isNotEmpty()){
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                ConversationList(
                    conversationMessages = conversationMessages,
                    listState = listState,
                    onRetry = onRetry
                )
            }
        } else{
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = stringResource(id = R.string.no_messages),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun ConversationList(
    modifier: Modifier = Modifier,
    onRetry: (ConversationMessage) -> Unit,
    conversationMessages: List<ConversationMessage>,
    listState: LazyListState
) {
    LazyColumn(
        reverseLayout = true,
        state = listState
    ) {
        items(
            conversationMessages.reversed(),
            key = {
                message ->
                message.timeStamp
            }
        ) {
            message ->
            ConversationBubble(
                conversationMessage = message,
                onRetry = onRetry
            )
        }
    }
}

@Composable
fun PromptAIBox(
    modifier: Modifier = Modifier,
    onSend: (String) -> Unit
) {

    var userMessage by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = DarkBlue50.copy(alpha = .6f))
            .padding(
                vertical = Dimens.screenVerticalPadding,
                horizontal = Dimens.screenHorizontalPadding
            )
    ) {
        OutlinedTextField(
            modifier = Modifier
                .dashedBorder(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = MaterialTheme.shapes.small
                )
                .fillMaxWidth(),
            value = userMessage,
            textStyle = MaterialTheme.typography.titleMedium,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.message),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSecondary.copy(
                            alpha = .5f
                        )
                    )
                )
            },
            trailingIcon = {
                AnimatedVisibility(visible = userMessage.isNotEmpty()) {
                    IconButton(onClick = {
                        if (userMessage.isNotEmpty()){
                            onSend(userMessage)
                            userMessage = ""
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sent),
                            contentDescription = stringResource(id = R.string.sent_icon),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                errorContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent
            ),
            onValueChange = {
                userMessage = it
            }
        )
    }
}

@Composable
fun ConversationBubble(
    modifier: Modifier = Modifier,
    conversationMessage: ConversationMessage,
    onRetry: (ConversationMessage) -> Unit,
) {

    // Scale state for animation
    var scale by remember { mutableFloatStateOf(if (conversationMessage.shouldAnimate) 0f else 1f) }

    // Animation effect
    LaunchedEffect(Unit) {
        scale = 1f
    }

    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = "chat bubble scale animation"
    )

    val isModelMessage = conversationMessage.senderType is SenderType.AIModel

    val backgroundColor = when (conversationMessage.senderType) {
        is SenderType.AIModel -> Orange300
        is SenderType.User -> MaterialTheme.colorScheme.primary
    }

    val senderName = when(conversationMessage.senderType){
        is SenderType.AIModel -> "Model"
        is SenderType.User -> conversationMessage.senderType.userName
    }

    val bubbleShape = if (isModelMessage) {
        RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
    } else {
        RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp)
    }

    val horizontalAlignment = if (isModelMessage) {
        Alignment.Start
    } else {
        Alignment.End
    }

    Column(
        horizontalAlignment = horizontalAlignment,
        modifier = modifier
            .scale(animatedScale)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = senderName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        BoxWithConstraints {
            Card(
                colors = CardDefaults.cardColors(containerColor = backgroundColor),
                shape = bubbleShape,
                modifier = Modifier.widthIn(0.dp, maxWidth * 0.9f)
            ) {
                BasicText(
                    text = parseMarkdown(conversationMessage.content),
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.onPrimary, fontSize = 15.sp
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        AnimatedContent(
            modifier = Modifier.padding(top = 3.dp),
            targetState = conversationMessage.conversationMessageStatus,
            label = "conversation message animated content animation"
        ) {
            messageStatus ->
            when(messageStatus){
                ConversationMessageStatus.SENDING ->{
                    ContractAICircularProgressIndicator(
                        strokeWidth = 2.dp,
                        size = 20.dp
                    )
                }
                ConversationMessageStatus.SENT ->{
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(color = Green600, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(15.dp),
                            imageVector = Icons.Filled.Done,
                            contentDescription = stringResource(id = R.string.message_sent_icon),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                ConversationMessageStatus.SEND_FAILED ->{
                    Row {
                        Icon(
                            modifier = Modifier
                                .size(20.dp),
                            painter = painterResource(id = R.drawable.ic_error_filled),
                            contentDescription = stringResource(id = R.string.error_icon),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            modifier = Modifier.clickable {
                                onRetry(conversationMessage)
                            },
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle()){
                                    append(stringResource(id = R.string.send_failed))
                                }
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                                    append(stringResource(id = R.string.retry))
                                }
                            },
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.error
                            )
                        )
                    }
                }
            }
        }
    }
}

private fun parseMarkdown(text: String): AnnotatedString {
    return buildAnnotatedString {
        val regex = """(\*\*[^*]+\*\*)|(\*[^*]+\*)""".toRegex()
        val matches = regex.findAll(text)

        var lastIndex = 0

        matches.forEach { matchResult ->
            val range = matchResult.range

            // Add any regular text before the match
            if (lastIndex < range.first) {
                append(text.substring(lastIndex, range.first))
            }

            val matchedText = matchResult.value

            when {
                matchedText.startsWith("**") && matchedText.endsWith("**") -> {
                    append(
                        AnnotatedString(
                            text = matchedText.removeSurrounding("**"),
                            spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
                        )
                    )
                }
                matchedText.startsWith("*") && matchedText.endsWith("*") -> {
                    append(
                        AnnotatedString(
                            text = matchedText.removeSurrounding("*"),
                            spanStyle = SpanStyle(fontStyle = FontStyle.Italic)
                        )
                    )
                }
            }

            lastIndex = range.last + 1
        }

        // Add any remaining regular text after the last match
        if (lastIndex < text.length) {
            append(text.substring(lastIndex))
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ConversationScreenPreview() {
    ContractAITheme {
        ConversationScreen(
            screenTitle = "Review contract of software",
            onNavigateBack = {},
            conversationMessages = listOf(),
            onRetry = {},
            onSend = {}
        )
    }
}