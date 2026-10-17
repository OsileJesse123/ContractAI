package com.jesse.contractai.di

import com.jesse.contractai.core.config.FirebaseRemoteConfig
import com.jesse.contractai.core.config.RemoteConfig
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class ConfigModule {

    @Binds
    abstract fun bindRemoteConfig(repoImpl: FirebaseRemoteConfig): RemoteConfig
}