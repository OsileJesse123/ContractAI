package com.jesse.contractai.ui.model

import com.jesse.contractai.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class ContractResult(
    val type: ContractHistoryType,
    val title: String,
    val timeStamp: Long
){
    class GeneratedContractResult(
        type: ContractHistoryType = ContractHistoryType.CONTRACT,
        title: String,
        timeStamp: Long
    ): ContractResult(type, title, timeStamp)

    class ReviewedContractResult(
        type: ContractHistoryType = ContractHistoryType.CONTRACT_REVIEW,
        title: String,
        timeStamp: Long
    ): ContractResult(type, title, timeStamp)

    fun getContractResultIcon() = when(type){
        ContractHistoryType.CONTRACT -> R.drawable.ic_add_file
        ContractHistoryType.CONTRACT_REVIEW -> R.drawable.ic_file_search
    }

    fun getContractResultIconDescription() = when(type){
        ContractHistoryType.CONTRACT -> R.string.add_file_icon
        ContractHistoryType.CONTRACT_REVIEW -> R.string.file_search_icon
    }

    fun getFormattedTime(): String {
        val dateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
        val date = Date(timeStamp)
        return dateFormat.format(date)
    }
}

enum class ContractHistoryType{
    CONTRACT,
    CONTRACT_REVIEW
}
/*data class ContractResult(
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
}*/

