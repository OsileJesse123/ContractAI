package com.jesse.contractai.data.network.model

import com.jesse.contractai.data.local.database.model.ReviewedContractEntity

data class ReviewContractResponse(
    val content: String,
    val title: String?,
){
    fun toReviewedContractEntity(timeStamp: Long, userId: String): ReviewedContractEntity = ReviewedContractEntity(
        timeStamp = timeStamp,
        title = title ?: "",
        userId = userId
    )
}
