package com.jesse.contractai.data.network.model

import com.jesse.contractai.data.local.database.model.GeneratedContractEntity
import com.jesse.contractai.ui.model.GeneratedContract

data class GenerateContractResponse(
    val title: String,
    val content: String
){
    fun toGeneratedContractEntity(timeStamp: Long, userId: String): GeneratedContractEntity = GeneratedContractEntity(
        timeStamp = timeStamp,
        title = title,
        content = content,
        userId = userId
    )
}
