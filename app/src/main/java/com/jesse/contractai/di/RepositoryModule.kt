package com.jesse.contractai.di

import com.jesse.contractai.data.repository.ContractRepository
import com.jesse.contractai.data.repository.ContractRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(repoImpl: ContractRepositoryImpl): ContractRepository
}