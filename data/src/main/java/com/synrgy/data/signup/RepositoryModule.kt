package com.synrgy.data.signup

import com.hafidh.domain.signup.SignUpRepository
import com.synrgy.data.di.NetworkModule
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
    fun bindSignUpRepository(signUpRepositoryImpl: SignUpRepositoryImpl): SignUpRepository
}