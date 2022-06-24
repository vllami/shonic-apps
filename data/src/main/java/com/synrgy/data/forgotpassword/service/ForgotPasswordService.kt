package com.synrgy.data.forgotpassword.service

import com.synrgy.data.forgotpassword.dto.*
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ForgotPasswordService {

    @Headers("Content-Type: application/json")
    @POST("forgotpassword/send")
    suspend fun sendForgotPasswordEmail(@Body forgotPasswordReq: ForgotPasswordReq): ForgotPasswordResp

    @POST("forgotpassword/validate")
    suspend fun validateForgotPassword(@Body req: ValidateOtpReq): ValidateOtpResp

    @POST("forgotpassword/reset_password")
    suspend fun resetPassword(@Body req: NewpasswordReq): NewPasswordResp
}