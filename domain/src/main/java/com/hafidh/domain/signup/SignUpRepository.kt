package com.hafidh.domain.signup

import com.hafidh.domain.common.WrapperResponse
import com.hafidh.domain.signup.model.SignUpDomain
import kotlinx.coroutines.flow.Flow


interface SignUpRepository {
    suspend fun checkEmail(email: String): Flow<WrapperResponse<SignUpDomain>>
    suspend fun verifyEmail(email: String, otp: Int): Flow<WrapperResponse<SignUpDomain>>
}