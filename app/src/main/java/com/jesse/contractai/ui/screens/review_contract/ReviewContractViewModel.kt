package com.jesse.contractai.ui.screens.review_contract

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.contractai.R
import com.jesse.contractai.data.repository.contract.ContractRepository
import com.jesse.contractai.data.repository.file_processing.FileProcessingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReviewContractViewModel @Inject constructor(
    private val fileProcessingRepository: FileProcessingRepository,
    private val contractRepository: ContractRepository
): ViewModel() {

    private val _reviewContractUiState: MutableStateFlow<ReviewContractUiState> = MutableStateFlow(ReviewContractUiState())
    val reviewContractUiState get() = _reviewContractUiState.asStateFlow()

    var reviewedContractTimeStamp: Long = 0
        private set

    fun reviewContract(pdfFile: File){
        viewModelScope.launch {
            _reviewContractUiState.update {
                    reviewContractUiState ->
                reviewContractUiState.copy(isLoading = true)
            }
            val contractContent = fileProcessingRepository.extractTextFromPdfWithIText(pdfFile)
            if (contractContent != null){
                val reviewedContractResult = contractRepository.reviewContract(contractContent)
                reviewedContractResult
                    .onSuccess {
                        reviewContract ->
                        reviewedContractTimeStamp = reviewContract.timeStamp
                        _reviewContractUiState.update {
                                reviewContractUiState ->
                            reviewContractUiState.copy(isLoading = false, navigateToNextScreen = true)
                        }
                    }
                    .onFailure {
                        _reviewContractUiState.update {
                                reviewContractUiState ->
                            reviewContractUiState.copy(isLoading = false, errorMessage = R.string.failed_to_review_contract)
                        }
                    }
            } else{
                _reviewContractUiState.update {
                    reviewContractUiState ->
                    reviewContractUiState.copy(isLoading = false, errorMessage = R.string.file_seems_to_be_empty)
                }
            }
        }
    }

    fun onNavigateToNextScreen(){
        _reviewContractUiState.update {
                reviewContractUiState ->
            reviewContractUiState.copy(navigateToNextScreen = false)
        }
    }

    fun onErrorMessageShown(){
        _reviewContractUiState.update {
                reviewContractUiState ->
            reviewContractUiState.copy(errorMessage = null)
        }
    }
}