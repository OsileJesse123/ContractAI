package com.jesse.contractai.ui.screens.create_contract

import androidx.lifecycle.ViewModel
import com.jesse.contractai.data.network.model.ContractOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateContractViewModel @Inject constructor(): ViewModel() {

    private val _createContractUiState: MutableStateFlow<CreateContractUiState> = MutableStateFlow(
        CreateContractUiState(
            contractOptions = listOf(
                ContractOption(isSelected = false, "Employment Contract"),
                ContractOption(isSelected = false, "Non Disclosure Agreement"),
                ContractOption(isSelected = false, "Intellectual Property Agreement"),
                ContractOption(isSelected = false, "Others"),
            )
        )
    )
    val createContractUiState get() = _createContractUiState.asStateFlow()

    fun onOptionSelected(index: Int){
        _createContractUiState.update {
            createContractState ->
            val newList = mutableListOf<ContractOption>()
            for ((i, contractOption) in createContractState.contractOptions.withIndex()){
                newList.add(contractOption.copy(isSelected = i == index))
            }
            val othersDescription = if (index != (createContractState.contractOptions.size - 1) && createContractState.othersDescription.isNotEmpty()){
                ""
            } else{
                createContractState.othersDescription
            }
            createContractState.copy(
                contractOptions = newList,
                othersDescription = othersDescription
            )
        }
    }

    fun onOthersDescriptionChange(description: String){
        _createContractUiState.update {
            createContractUiState ->
            createContractUiState.copy(othersDescription = description)
        }
    }

    fun onContinue(): String?{
        return _createContractUiState.value.othersDescription.ifEmpty {
            _createContractUiState.value.contractOptions.find {
                it.isSelected
            }?.optionName
        }
    }
}