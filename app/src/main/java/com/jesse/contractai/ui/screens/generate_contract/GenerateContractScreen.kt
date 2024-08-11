package com.jesse.contractai.ui.screens.generate_contract

import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesse.contractai.R
import com.jesse.contractai.ui.components.ContractAICircularProgressIndicator
import com.jesse.contractai.ui.components.ContractAISnackBar
import com.jesse.contractai.ui.model.GeneratedContract
import com.jesse.contractai.ui.modifiers.dashedBorder
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.ui.theme.DarkBlue50
import com.jesse.contractai.ui.theme.Dimens
import com.jesse.contractai.ui.theme.Green600
import kotlinx.coroutines.delay


@Composable
fun GenerateContractRoute(
    modifier: Modifier = Modifier,
    timeStamp: Long,
    viewModel: GenerateContractViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit
) {
    val uiState by viewModel.generateContractUiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
       viewModel.getGeneratedContract(timeStamp)
    }

    GenerateContractScreen(
        onNavigateHome = onNavigateHome,
        uiState = uiState,
        onSendPrompt = {
            complaints ->
            viewModel.generateNewContract(complaints)
        },
        onSnackBarShown = {
            viewModel.onErrorMessageShown()
        }
    )
}

@Composable
fun GenerateContractScreen(
    uiState: GenerateContractUiState,
    onNavigateHome: () -> Unit,
    onSendPrompt: (String) -> Unit,
    onSnackBarShown: () -> Unit
) {
    val horizontalPadding = 16.dp
    val context = LocalContext.current
    var webView: WebView? = null

    var showSatisfactionConfirmationDialog by remember {
        mutableStateOf(false)
    }

    var showSatisfactionConfirmedDialog by remember {
        mutableStateOf(false)
    }

    var showPromptAIBox by remember {
        mutableStateOf(false)
    }

    if (showSatisfactionConfirmationDialog){
        SatisfactionConfirmationDialog(
            onDismiss = { showSatisfactionConfirmationDialog = false },
            onYesClicked = {
                showSatisfactionConfirmationDialog = false
                showSatisfactionConfirmedDialog = true
            },
            onNoClicked = {
                showSatisfactionConfirmationDialog = false
                showPromptAIBox = true
            }
        )
    }

    if(showSatisfactionConfirmedDialog){
        SatisfactionConfirmedDialog(
            onDismiss = {
                showSatisfactionConfirmedDialog = false
            }
        )
    }

    AnimatedContent(
        targetState = uiState,
        label = "generate contract screen animation"
    ) {
        generatedContractUiState ->

        if (generatedContractUiState.generatedContract != null && !generatedContractUiState.isLoading){
            var promptValue by remember {
                mutableStateOf("")
            }
            Scaffold(
                topBar = {
                    ContractAISnackBar(
                        message = uiState.errorMessage,
                        onSnackBarShown = onSnackBarShown
                    )
                },
                bottomBar = {
                    AnimatedVisibility(
                        visible = showPromptAIBox,
                        enter = slideInVertically(
                            initialOffsetY = {-it}
                        ),
                        exit = slideOutVertically(
                            targetOffsetY = {-it}
                        )
                    ) {
                        PromptAIBox(
                            value = promptValue,
                            onSend = {
                                if (promptValue.isNotEmpty()){
                                    onSendPrompt(promptValue)
                                    promptValue = ""
                                    showPromptAIBox = false
                                }
                            },
                            onValueChange = {
                                promptValue = it
                            }
                        )
                    }
                }
            ) {
                paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ){
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 30.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(id = R.string.your_contract_is_ready),
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 26.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = stringResource(id = R.string.review_and_revise_contract),
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Column(
                            modifier = Modifier
                                .weight(2.5f)
                                .background(
                                    color = MaterialTheme.colorScheme.tertiary.copy(
                                        alpha = .1f
                                    )
                                )
                                .dashedBorder(
                                    color = MaterialTheme.colorScheme.tertiary,
                                    shape = MaterialTheme.shapes.small
                                )
                        ){
                            Column(
                                modifier = Modifier
                                    .verticalScroll(rememberScrollState())
                                    .weight(2f)
                            ) {
                                AndroidView(
                                    modifier = Modifier.fillMaxSize(),
                                    factory = { ctx ->
                                        WebView(ctx).apply {
                                            setBackgroundColor(Color.Transparent.toArgb())
                                            webViewClient = WebViewClient()
                                            loadDataWithBaseURL(null, generatedContractUiState.generatedContract.content, "text/html", "UTF-8", null)
                                        }
                                    },
                                    update = { view ->
                                        webView = view
                                    }
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(.5f)
                                    .padding(horizontal = horizontalPadding, vertical = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Spacer(modifier = Modifier.height(height = 10.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .dashedBorder(
                                            color = MaterialTheme.colorScheme.outlineVariant,
                                            shape = RectangleShape,
                                            dashWidth = 2.dp,
                                            gapWidth = 6.dp
                                        )
                                ){}
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(1.dp)
                                ) {
                                    IconButton(
                                        onClick = onNavigateHome
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(25.dp),
                                            painter = painterResource(id = R.drawable.ic_home),
                                            contentDescription = stringResource(id = R.string.home_icon),
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            showSatisfactionConfirmationDialog = true
                                        }
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(25.dp),
                                            painter = painterResource(id = R.drawable.ic_bot),
                                            contentDescription = stringResource(id = R.string.bot_icon),
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            exportAsPdf(webView, context, generatedContractUiState.generatedContract.title)
                                        }
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(23.dp),
                                            painter = painterResource(id = R.drawable.ic_download),
                                            contentDescription = stringResource(id = R.string.download_icon),
                                            tint = Green600
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            modifier = Modifier.weight(.5f),
                            text = stringResource(id = R.string.contract_ai_disclaimer),
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = MaterialTheme.colorScheme.tertiary
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        if (generatedContractUiState.generatedContract == null){
            Box(
                modifier = Modifier
                    .wrapContentSize(),
                contentAlignment = Alignment.Center){
                Text(
                    text = stringResource(id = R.string.no_generated_contract),
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }

        if (generatedContractUiState.isLoading){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                ContractAICircularProgressIndicator()
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.re_generating_contract),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                )
            }
        }
    }
}

@Composable
fun SatisfactionConfirmationDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onYesClicked: () -> Unit,
    onNoClicked: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = modifier
                .width(300.dp)
                .height(200.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = Dimens.marginSmall,
                        vertical = Dimens.marginSmall
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bot),
                    contentDescription = stringResource(id = R.string.bot_icon)
                )
                Spacer(modifier = Modifier.height(height = 20.dp))
                Text(
                    text = stringResource(id = R.string.are_you_satisfied),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(height = 30.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ElevatedButton(
                        onClick = onYesClicked) {
                        Text(text = stringResource(id = R.string.yes))
                    }
                    ElevatedButton(
                        onClick = onNoClicked) {
                        Text(text = stringResource(id = R.string.no))
                    }
                }
            }
        }
    }
}

@Composable
fun SatisfactionConfirmedDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        delay(3_000)
        onDismiss()
    }
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = modifier
                .width(300.dp)
                .height(250.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = Dimens.marginSmall,
                        vertical = Dimens.marginSmall
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(40.dp),
                    painter = painterResource(id = R.drawable.ic_bot),
                    contentDescription = stringResource(id = R.string.bot_icon)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.satisfied_message),
                    style = MaterialTheme.typography.titleMedium.copy(
                        lineHeight = 24.sp
                    ),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(30.dp))
                ElevatedButton(
                    onClick = onDismiss
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        }
    }
}

@Composable
fun PromptAIBox(
    modifier: Modifier = Modifier,
    value: String,
    onSend: () -> Unit,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = DarkBlue50)
            .padding(
                vertical = Dimens.screenVerticalPadding,
                horizontal = Dimens.screenHorizontalPadding
            )
            .height(height = 150.dp)
    ) {
        Text(
            text = stringResource(id = R.string.why_are_you_unsatisfied),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            modifier = Modifier
                .dashedBorder(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = MaterialTheme.shapes.small
                )
                .fillMaxWidth(),
            value = value,
            textStyle = MaterialTheme.typography.titleMedium,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.enter_complaint_here),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSecondary.copy(
                            alpha = .5f
                        )
                    )
                )
            },
            trailingIcon = {
                 AnimatedVisibility(visible = value.isNotEmpty()) {
                     IconButton(onClick = onSend) {
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
            onValueChange = onValueChange
        )
    }
}

fun exportAsPdf(webView: WebView?, context: Context, documentName: String) {
    if (webView != null) {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
        val printAdapter =
            webView.createPrintDocumentAdapter(documentName)
        val printAttributes = PrintAttributes.Builder()
            .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            .build()
        printManager.print(documentName,
            printAdapter,
            printAttributes)
    }
}

@Preview(showBackground = true)
@Composable
fun GenerateContractScreenPreview() {
    ContractAITheme {
        GenerateContractScreen(
            onNavigateHome = {},
            uiState = GenerateContractUiState(
                generatedContract = GeneratedContract(
                    timeStamp = 0,
                    title = "Title",
                    content = "Content"
                )
            ),
            onSendPrompt = {},
            onSnackBarShown = {}
        )
    }
}