package com.synrgy.data.signup.dto

data class CompleteSignUpReq(
    val email: String,
    val fullName: String,
    val password: String,
)