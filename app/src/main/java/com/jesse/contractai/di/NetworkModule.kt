package com.jesse.contractai.di

import com.google.ai.client.generativeai.GenerativeModel
import com.jesse.contractai.data.network.data_source.ContractNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun providesContractNetworkDataSource(generativeAIModel: GenerativeModel) =
        ContractNetworkDataSource(generativeAIModel)
}
