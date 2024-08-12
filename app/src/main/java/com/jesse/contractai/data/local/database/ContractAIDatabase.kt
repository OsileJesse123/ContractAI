package com.jesse.contractai.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jesse.contractai.data.local.database.dao.ConversationItemDao
import com.jesse.contractai.data.local.database.dao.GeneratedContractDao
import com.jesse.contractai.data.local.database.dao.ReviewedContractDao
import com.jesse.contractai.data.local.database.model.ConversationItemEntity
import com.jesse.contractai.data.local.database.model.GeneratedContractEntity
import com.jesse.contractai.data.local.database.model.ReviewedContractEntity

@Database(entities = [GeneratedContractEntity::class, ReviewedContractEntity::class, ConversationItemEntity::class], version = 1)
abstract class ContractAIDatabase: RoomDatabase() {

    abstract fun generatedContractDao(): GeneratedContractDao
    abstract fun reviewedContractDao(): ReviewedContractDao
    abstract fun conversationItemDao(): ConversationItemDao
}