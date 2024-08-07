package com.jesse.contractai.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jesse.contractai.data.local.database.dao.GeneratedContractDao
import com.jesse.contractai.data.local.database.model.GeneratedContractEntity

@Database(entities = [GeneratedContractEntity::class], version = 1)
abstract class ContractAIDatabase: RoomDatabase() {

    abstract fun generatedContractDao(): GeneratedContractDao
}