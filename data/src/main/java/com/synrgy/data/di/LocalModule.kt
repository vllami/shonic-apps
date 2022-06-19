package com.synrgy.data.di

import android.content.Context
import com.hafidh.domain.common.PreferenceProvider
import com.synrgy.data.common.SharedPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): PreferenceProvider {
        return SharedPreferencesImpl(context)
    }
}