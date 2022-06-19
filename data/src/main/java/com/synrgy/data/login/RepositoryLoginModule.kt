package com.synrgy.data.login

import com.hafidh.domain.login.LoginRepository
import com.synrgy.data.di.LocalModule
import com.synrgy.data.di.NetworkModule
import com.synrgy.data.login.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, LocalModule::class])
@InstallIn(SingletonComponent::class)
interface RepositoryLoginModule {
    @Binds
    @Singleton
    fun provideLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository
}