package com.jesse.contractai.di

import com.jesse.contractai.data.local.data_source.ContractLocalDataSource
import com.jesse.contractai.data.local.data_source.ContractLocalDataSourceImpl
import com.jesse.contractai.data.network.data_source.ContractNetworkDataSource
import com.jesse.contractai.data.network.data_source.ContractNetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class DatasourceModule {

    @Binds
    abstract fun providesContractNetworkDataSource(dataSourceImpl: ContractNetworkDataSourceImpl): ContractNetworkDataSource

    @Binds
    abstract fun providesContractLocalDataSource(dataSourceImpl: ContractLocalDataSourceImpl): ContractLocalDataSource
}
