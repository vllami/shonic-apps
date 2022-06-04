package com.synrgy.data.di

import com.hafidh.domain.signup.SignUpRepository
import com.synrgy.data.BuildConfig
import com.synrgy.data.signup.repository.SignUpRepositoryImpl
import com.synrgy.data.signup.service.SignUpService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://shonic-test.herokuapp.com/api/v1")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) okHttpClient.addInterceptor(
            logging.setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        )
        else okHttpClient.addInterceptor(
            logging.setLevel(
                HttpLoggingInterceptor.Level.BASIC
            )
        )
        okHttpClient.connectTimeout(120, TimeUnit.SECONDS)
        okHttpClient.readTimeout(120, TimeUnit.SECONDS)
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideSignUpService(retrofit: Retrofit): SignUpService {
        return retrofit.create(SignUpService::class.java)
    }
}

