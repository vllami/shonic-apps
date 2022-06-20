package com.synrgy.data.di

import com.hafidh.domain.forgotpassword.ForgotPasswordRepository
import com.hafidh.domain.login.LoginRepository
import com.hafidh.domain.signup.SignUpRepository
import com.synrgy.data.forgotpassword.repository.ForgotPasswordImpl
import com.synrgy.data.login.repository.LoginRepositoryImpl
import com.synrgy.data.signup.repository.SignUpRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun provideLoginRepository(repositoryImpl: LoginRepositoryImpl) : LoginRepository

    @Binds
    @Singleton
    fun provideSignUpRepository(repositoryImpl: SignUpRepositoryImpl) : SignUpRepository

    @Binds
    @Singleton
    fun provideForgotPasswordRepository(repositoryImpl: ForgotPasswordImpl) : ForgotPasswordRepository
}