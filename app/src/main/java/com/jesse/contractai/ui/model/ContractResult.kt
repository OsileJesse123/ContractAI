package com.jesse.contractai.ui.model

import com.jesse.contractai.R

data class ContractResult(
    val id: String,
    val type: ContractHistoryType,
    val title: String,
    val content: String,
    val timeStamp: String
){
    fun getContractResultIcon() = when(type){
        ContractHistoryType.CONTRACT -> R.drawable.ic_add_file
        ContractHistoryType.CONTRACT_REVIEW -> R.drawable.ic_file_search
    }

    fun getContractResultIconDescription() = when(type){
        ContractHistoryType.CONTRACT -> R.string.add_file_icon
        ContractHistoryType.CONTRACT_REVIEW -> R.string.file_search_icon
    }
}

enum class ContractHistoryType{
    CONTRACT,
    CONTRACT_REVIEW
}
