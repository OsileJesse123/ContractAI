package com.jesse.contractai.di

import com.jesse.contractai.data.network.service.user_auth_service.UserAuthenticationService
import com.jesse.contractai.data.network.service.user_auth_service.UserAuthenticationServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesUserAuthenticationService(
        userAuthenticationServiceImpl: UserAuthenticationServiceImpl
    ): UserAuthenticationService = userAuthenticationServiceImpl
}