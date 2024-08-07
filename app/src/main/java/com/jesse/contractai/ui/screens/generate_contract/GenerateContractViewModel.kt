package com.jesse.contractai.ui.screens.generate_contract

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.contractai.R
import com.jesse.contractai.data.repository.contract.ContractRepository
import com.jesse.contractai.ui.model.GeneratedContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenerateContractViewModel @Inject constructor(
    private val contractRepository: ContractRepository
): ViewModel() {

    private val _generateContractUiState: MutableStateFlow<GenerateContractUiState> =
        MutableStateFlow(GenerateContractUiState())
    val generateContractUiState get() =
        _generateContractUiState.asStateFlow()

    /*var generatedContract = mutableStateOf<GeneratedContract?>(null)
        private set*/

    fun getGeneratedContract(timeStamp: Long){
        viewModelScope.launch {
            val result = contractRepository.getGeneratedContract(timeStamp)
            result.onSuccess {
                contract ->
                _generateContractUiState.update {
                    it.copy(generatedContract = contract)
                }
            }
        }
    }

    fun generateNewContract(complaints: String){
       viewModelScope.launch {
           _generateContractUiState.update {
                   generateContractUiState ->
               generateContractUiState.copy(isLoading = true)
           }
           _generateContractUiState.value.generatedContract?.let {
               contract ->
               val result = contractRepository.regenerateAnotherContract(
                   complaints = complaints,
                   generatedContract = contract
               )
               result
                   .onSuccess {
                       _generateContractUiState.update {
                               generateContractUiState ->
                           generateContractUiState.copy(isLoading = false, generatedContract = it)
                       }
                   }
                   .onFailure {
                       _generateContractUiState.update {
                               generateContractUiState ->
                           generateContractUiState.copy(
                               isLoading = false,
                               errorMessage = R.string.failed_to_regenerate_contract
                           )
                       }
                   }
           }
       }
    }

    fun onErrorMessageShown(){
        _generateContractUiState.update {
                generateContractUiState ->
            generateContractUiState.copy(errorMessage = null)
        }
    }
}