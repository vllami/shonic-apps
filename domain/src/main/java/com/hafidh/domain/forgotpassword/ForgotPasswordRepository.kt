package com.hafidh.domain.forgotpassword

import com.hafidh.domain.common.WrapperResponse
import com.hafidh.domain.forgotpassword.model.ForgotPasswordDomain
import kotlinx.coroutines.flow.Flow

interface ForgotPasswordRepository {
    suspend fun sendEmailRequest(email: String) : Flow<WrapperResponse<ForgotPasswordDomain>>
    suspend fun validateOTP(email: String, otp: Int) : Flow<WrapperResponse<ForgotPasswordDomain>>
    suspend fun changePassword(email: String, newPassword: String, token: String) : Flow<WrapperResponse<ForgotPasswordDomain>>
}