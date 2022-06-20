package com.synrgy.data.di

import android.content.Context
import com.hafidh.domain.common.PreferenceProvider
import com.synrgy.data.BuildConfig
import com.synrgy.data.common.RequestInterceptor
import com.synrgy.data.common.SharedPreferencesImpl
import com.synrgy.data.forgotpassword.service.ForgotPasswordService
import com.synrgy.data.login.service.LoginService
import com.synrgy.data.signup.service.SignUpService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
            .baseUrl("http://shonic-test.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(requestInterceptor: RequestInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(requestInterceptor).also { client ->
                if (BuildConfig.DEBUG){
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }
        }.build()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): PreferenceProvider {
        return SharedPreferencesImpl(context)
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

    @Provides
    @Singleton
    fun provideForgotPasswordService(retrofit: Retrofit): ForgotPasswordService {
        return retrofit.create(ForgotPasswordService::class.java)
    }

}

