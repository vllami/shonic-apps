package com.synrgy.data.di

import com.hafidh.domain.common.PreferenceProvider
import com.synrgy.data.BuildConfig
import com.synrgy.data.common.RequestInterceptor
import com.synrgy.data.login.service.LoginService
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

@Module(includes = [LocalModule::class])
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://shonic-test.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(requestInterceptor: RequestInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        okHttpClient.addInterceptor(requestInterceptor).also {
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
                okHttpClient.addInterceptor(logging)
            } else {
                logging.level = HttpLoggingInterceptor.Level.NONE
                okHttpClient.addInterceptor(logging)
            }
        }
        okHttpClient.connectTimeout(120, TimeUnit.SECONDS)
        okHttpClient.readTimeout(120, TimeUnit.SECONDS)
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRequestInterceptor(provider: PreferenceProvider): RequestInterceptor {
        return RequestInterceptor(provider)
    }

    @Provides
    @Singleton
    fun provideSignUpService(retrofit: Retrofit): SignUpService {
        return retrofit.create(SignUpService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }


}

