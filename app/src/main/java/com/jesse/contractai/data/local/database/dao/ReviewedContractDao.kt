package com.jesse.contractai.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jesse.contractai.data.local.database.model.ReviewedContractEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewedContractDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviewedContract(reviewedContract: ReviewedContractEntity)

    @Query("SELECT * FROM reviewed_contract WHERE time_stamp = :timeStamp")
    suspend fun getReviewedContract(timeStamp: Long): ReviewedContractEntity

    @Query("SELECT * FROM reviewed_contract WHERE user_id = :userId")
    fun getAllReviewedContracts(userId: String): Flow<List<ReviewedContractEntity>>

}