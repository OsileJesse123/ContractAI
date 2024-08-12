package com.jesse.contractai.ui.screens.review_contract

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor
import com.jesse.contractai.R
import com.jesse.contractai.ui.components.ContractAIBackButton
import com.jesse.contractai.ui.components.ContractAIButton
import com.jesse.contractai.ui.components.ContractAICircularProgressIndicator
import com.jesse.contractai.ui.components.ContractAISnackBar
import com.jesse.contractai.ui.modifiers.dashedBorder
import com.jesse.contractai.ui.theme.ContractAITheme
import com.jesse.contractai.ui.theme.Dimens
import com.jesse.contractai.ui.theme.Green600
import com.jesse.contractai.util.FileUtil
import java.io.File


@Composable
fun ReviewContractRoute(
    modifier: Modifier = Modifier,
    viewModel: ReviewContractViewModel = hiltViewModel(),
    onNavigateToNextScreen: (Long) -> Unit,
    onNavigateBack: () -> Unit
) {

    val uiState by viewModel.reviewContractUiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val fileUtil = FileUtil(context)
    var fileDetails by rememberSaveable {
        mutableStateOf<FileDetails?>(null)
    }
    var selectedPdfUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data: Intent? = result.data
        val uri: Uri? = data?.data
        if (uri != null) {
            selectedPdfUri = uri
        }
    }
    LaunchedEffect(uiState.navigateToNextScreen) {
        if (uiState.navigateToNextScreen){
            onNavigateToNextScreen(viewModel.reviewedContractTimeStamp)
            viewModel.onNavigateToNextScreen()
        }
    }
    LaunchedEffect(key1 = selectedPdfUri) {
        selectedPdfUri?.let {
            uri ->
            val fileName = fileUtil.getFileName(uri)
            val fileSize = fileUtil.getReadableFileSize(fileUtil.getFileSize(uri))
            fileDetails = FileDetails(fileName, fileSize, fileUtil.getFileFromUri(uri))
        }
        if (selectedPdfUri == null){
            fileDetails = null
        }
    }
    ReviewContractScreen(
        modifier = modifier,
        uploadPDF = {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/pdf"
            }
            launcher.launch(intent)
        },
        fileDetails = fileDetails,
        onDelete = {
            selectedPdfUri = null
        },
        onNavigateBack = onNavigateBack,
        uiState = uiState,
        onSnackBarShown = {
            viewModel.onErrorMessageShown()
        },
        onReviewContract = {
            file ->
            viewModel.reviewContract(file)
        }
    )
}

@Composable
fun ReviewContractScreen(
    modifier: Modifier = Modifier,
    uploadPDF: () -> Unit,
    onDelete: () -> Unit,
    onNavigateBack: () -> Unit,
    onSnackBarShown: () -> Unit,
    onReviewContract: (File) -> Unit,
    fileDetails: FileDetails?,
    uiState: ReviewContractUiState
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = Dimens.screenHorizontalPadding,
                vertical = Dimens.screenVerticalPadding
            )
    ) {
        ContractAISnackBar(
            modifier = Modifier,
            message = uiState.errorMessage,
            onSnackBarShown = onSnackBarShown
        )
        ContractAIBackButton(
            modifier = Modifier.align(Alignment.TopStart),
            onClick = onNavigateBack
        )
        AnimatedContent(
            targetState = uiState.isLoading,
            label = "review contract screen animated content"
        ) {
            isLoading ->
            if(!isLoading){
                Box(modifier = Modifier.fillMaxSize()){
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)

                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .dashedBorder(
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    shape = MaterialTheme.shapes.small
                                )
                                .clickable(onClick = uploadPDF),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Image(
                                modifier = Modifier.padding(top = Dimens.marginExtraSmall),
                                painter = painterResource(id = R.drawable.ic_file_import),
                                contentDescription = stringResource(id = R.string.import_file_icon)
                            )
                            Text(
                                text = stringResource(id = R.string.upload_files_here),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                modifier = Modifier.padding(bottom = Dimens.marginExtraSmall),
                                text = stringResource(id = R.string.pdf_files_only),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = 12.sp, color = MaterialTheme.colorScheme.error
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(height = Dimens.marginSmall))
                        AnimatedVisibility(visible = fileDetails != null) {
                            SelectedFileComposable(
                                fileName = fileDetails?.formatFileName() ?: "",
                                fileSize = fileDetails?.fileSize ?: "",
                                onDelete = onDelete
                            )
                        }
                    }

                    ContractAIButton(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        text = stringResource(id = R.string.review_contract),
                        onClick = {
                            fileDetails?.file?.let{
                                file ->
                                onReviewContract(file)
                            }
                        },
                        icon = R.drawable.ic_file_search,
                        iconDescription = R.string.file_search_icon,
                        enabled = fileDetails != null
                    )
                }
            } else{
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    ContractAICircularProgressIndicator()
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.your_contract_is_being_reviewed),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    )
                }
            }
        }
    }
}



@Composable
fun SelectedFileComposable(
    modifier: Modifier = Modifier,
    fileName: String,
    fileSize: String,
    onDelete: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .dashedBorder(
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = MaterialTheme.shapes.small
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(3.dp)
                    )
                    .padding(5.dp),
                painter = painterResource(id = R.drawable.ic_file),
                contentDescription = stringResource(id = R.string.file_icon),
                colorFilter = ColorFilter.tint(color = Green600)
            )
            Spacer(modifier = Modifier.width(width = 5.dp))
            Column {
                Text(
                    text = fileName,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 14.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = fileSize,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                )
            }
        }
        IconButton(
            onClick = onDelete
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = stringResource(id = R.string.delete_icon),
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ReviewContractScreenPreview() {
    ContractAITheme {
        ReviewContractScreen(
            uploadPDF = {},
            fileDetails = null,
            onDelete = {},
            onNavigateBack = {},
            uiState = ReviewContractUiState(),
            onSnackBarShown = {},
            onReviewContract = {}
        )
    }
}