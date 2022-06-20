package com.hafidh.domain.forgotpassword.model

data class ForgotPasswordDomain(
    val message: String?,
    val status: Int?,
    val token: String?
)
