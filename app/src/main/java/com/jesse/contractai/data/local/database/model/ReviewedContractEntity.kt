package com.jesse.contractai.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jesse.contractai.ui.model.ReviewedContract

@Entity(tableName = "reviewed_contract")
data class ReviewedContractEntity(
    @PrimaryKey
    @ColumnInfo(name = "time_stamp")
    val timeStamp: Long,
    val title: String,
    @ColumnInfo(name = "user_id")
    val userId: String
){
    fun toReviewedContract() = ReviewedContract(timeStamp = timeStamp, title = title)
}
