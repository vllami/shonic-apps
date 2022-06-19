package com.synrgy.data.common

import com.hafidh.domain.common.PreferenceProvider
import com.hafidh.domain.login.usecase.LoginUseCase.Companion.TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RequestInterceptor @Inject constructor(private val preferenceProvider: PreferenceProvider): Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = preferenceProvider.getString(TOKEN)
        val request = chain.request()
        val newRequest = if(token.isNotEmpty()){
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        }else{
            request
        }
        return chain.proceed(newRequest)
    }
}