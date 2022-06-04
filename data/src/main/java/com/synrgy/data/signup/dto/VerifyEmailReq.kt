package com.synrgy.data.signup.dto

data class VerifyEmailReq(
    val email: String,
    val otp: Int
)
