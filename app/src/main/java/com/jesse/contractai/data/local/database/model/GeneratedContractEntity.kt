package com.jesse.contractai.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jesse.contractai.ui.model.GeneratedContract


@Entity(tableName = "generated_contract")
data class GeneratedContractEntity(
    @PrimaryKey
    @ColumnInfo(name = "time_stamp")
    val timeStamp: Long,
    val title: String,
    val content: String,
    @ColumnInfo(name = "user_id")
    val userId: String
){
    fun toGeneratedContract(): GeneratedContract = GeneratedContract(title = title, content = content, timeStamp = timeStamp)
}
