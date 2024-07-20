package com.jesse.contractai.di

import com.jesse.contractai.domain.use_case.ValidateEmailUseCase
import com.jesse.contractai.domain.use_case.ValidateNameUseCase
import com.jesse.contractai.domain.use_case.ValidatePasswordStrengthUseCase
import com.jesse.contractai.domain.use_case.ValidatePasswordUseCase
import com.jesse.contractai.util.EmailMatcher
import com.jesse.contractai.util.EmailMatcherImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun providesEmailMatcher(emailMatcherImpl: EmailMatcherImpl): EmailMatcher = emailMatcherImpl


    @Provides
    fun provideValidateEmailUseCase(emailMatcher: EmailMatcher) = ValidateEmailUseCase(emailMatcher)

    @Provides
    fun provideValidatePasswordUseCase() = ValidatePasswordUseCase()

    @Provides
    fun provideValidateNameUseCase() = ValidateNameUseCase()

    @Provides
    fun provideValidatePasswordStrengthUseCase() = ValidatePasswordStrengthUseCase()
}