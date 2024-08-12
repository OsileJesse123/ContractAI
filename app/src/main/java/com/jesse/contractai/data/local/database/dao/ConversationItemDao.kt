package com.jesse.contractai.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jesse.contractai.data.local.database.model.ConversationItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversationItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversationItem(conversationItem: ConversationItemEntity)

    @Update
    suspend fun updateConversationItem(conversationItem: ConversationItemEntity)

    @Query("SELECT * FROM conversation_item WHERE reviewed_contract_time_stamp = :reviewedContractTimeStamp")
    fun getAllConversationItems(reviewedContractTimeStamp: Long): Flow<List<ConversationItemEntity>>
}