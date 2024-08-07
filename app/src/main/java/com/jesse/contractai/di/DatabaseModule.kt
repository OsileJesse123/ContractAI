package com.jesse.contractai.di

import android.content.Context
import androidx.room.Room
import com.jesse.contractai.data.local.database.ContractAIDatabase
import com.jesse.contractai.data.local.database.passphrase.DatabasePassphrase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabasePassphrase(@ApplicationContext context: Context) = DatabasePassphrase(context)

    @Provides
    @Singleton
    fun provideSupportFactory(databasePassphrase: DatabasePassphrase) = SupportFactory(databasePassphrase.getPassphrase())

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context, supportFactory: SupportFactory): ContractAIDatabase{
        return Room.databaseBuilder(context, ContractAIDatabase::class.java, "contract_ai_database")
            .openHelperFactory(supportFactory)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipeDao(contractAIDatabase: ContractAIDatabase) = contractAIDatabase.generatedContractDao()

}