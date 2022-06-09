package com.synrgy.data.signup.service

import com.synrgy.data.signup.dto.*
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("otp/send")
    suspend fun sendOtp(@Body emailReq: CheckEmailReq): SignUpResp

    @POST("otp/validate")
    suspend fun validateOtp(@Body verifyEmailReq: VerifyEmailReq): SignUpResp

    @POST("auth/register")
    suspend fun signUp(@Body signUpReq: CompleteSignUpReq): CompleteSignUpResp

}