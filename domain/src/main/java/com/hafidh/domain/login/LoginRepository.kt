package com.hafidh.domain.login

import com.hafidh.domain.common.WrapperResponse
import com.hafidh.domain.login.model.LoginDomain
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(email: String, password: String): Flow<WrapperResponse<LoginDomain>>
}