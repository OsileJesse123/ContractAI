package com.jesse.contractai.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jesse.contractai.data.local.database.model.GeneratedContractEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GeneratedContractDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeneratedContract(generatedContract: GeneratedContractEntity)

    @Update
    suspend fun updateGeneratedContract(generatedContract: GeneratedContractEntity)

    @Query("DELETE FROM generated_contract WHERE time_stamp = :timeStamp")
    suspend fun deleteGeneratedContract(timeStamp: Long)

    @Query("SELECT * FROM generated_contract WHERE time_stamp = :timeStamp")
    suspend fun getGeneratedContract(timeStamp: Long): GeneratedContractEntity

    @Query("SELECT * FROM generated_contract WHERE user_id = :userId")
    fun getAllGeneratedContract(userId: String): Flow<List<GeneratedContractEntity>>
}