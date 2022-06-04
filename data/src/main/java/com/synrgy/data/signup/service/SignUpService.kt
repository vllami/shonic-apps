package com.synrgy.data.signup.service

import com.synrgy.data.signup.dto.SignUpResp
import com.synrgy.data.signup.dto.VerifyEmailReq
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("/otp/send")
    suspend fun sendOtp(@Body email: String): SignUpResp

    @POST("/otp/validate")
    suspend fun validateOtp(@Body verifyEmailReq: VerifyEmailReq): SignUpResp
}