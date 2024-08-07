package com.jesse.contractai.ui.screens.create_contract

import com.jesse.contractai.data.network.model.ContractOption

data class CreateContractUiState(
    val contractOptions: List<ContractOption> = listOf(),
    val othersDescription: String = ""
) {
    fun isButtonEnabled(): Boolean{
        return if (contractOptions.isNotEmpty()){
            contractOptions.any { it.isSelected && it.optionName != "Others" } || othersDescription.isNotEmpty()
        } else{
            false
        }
    }

    fun showOthersTextField(): Boolean{
        return if(contractOptions.isNotEmpty()){
            contractOptions.any { it.isSelected && it.optionName == "Others" }
        } else{
            false
        }
    }
}
