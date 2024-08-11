package com.jesse.contractai.di

import com.jesse.contractai.data.repository.ai_chat.AIChatRepository
import com.jesse.contractai.data.repository.ai_chat.AIChatRepositoryImpl
import com.jesse.contractai.data.repository.contract.ContractRepository
import com.jesse.contractai.data.repository.contract.ContractRepositoryImpl
import com.jesse.contractai.data.repository.file_processing.FileProcessingRepository
import com.jesse.contractai.data.repository.file_processing.FileProcessingRepositoryImpl
import com.jesse.contractai.data.repository.user_authentication.UserAuthenticationRepository
import com.jesse.contractai.data.repository.user_authentication.UserAuthenticationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindContractRepository(repoImpl: ContractRepositoryImpl): ContractRepository

    @Binds
    abstract fun bindUserAuthenticationRepository(repoImpl: UserAuthenticationRepositoryImpl): UserAuthenticationRepository

    @Binds
    abstract fun bindFileProcessingRepository(repoImpl: FileProcessingRepositoryImpl): FileProcessingRepository

    @Binds
    abstract fun bindAIChatRepository(repoImpl: AIChatRepositoryImpl): AIChatRepository
}