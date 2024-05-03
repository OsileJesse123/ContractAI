package com.jesse.contractai.ui.screens.create_contract

import com.jesse.contractai.data.model.ContractOption

data class CreateContractUiState(
    val contractOptions: List<ContractOption> = listOf(),
) {
    fun isButtonEnabled(): Boolean{
        return if (contractOptions.isNotEmpty()){
            contractOptions.any {
                it.isSelected
            }
        } else{
            false
        }
    }
}
