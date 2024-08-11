package com.jesse.contractai.data.network.model

import com.jesse.contractai.data.local.database.model.GeneratedContractEntity
import com.jesse.contractai.data.local.database.model.ReviewedContractEntity

data class ReviewContractResponse(
    val title: String,
    val content: String
){
    fun toReviewedContractEntity(timeStamp: Long, userId: String): ReviewedContractEntity = ReviewedContractEntity(
        timeStamp = timeStamp,
        title = title,
        userId = userId
    )
}
