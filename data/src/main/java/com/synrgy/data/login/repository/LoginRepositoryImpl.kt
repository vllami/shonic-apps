package com.synrgy.data.login.repository

import android.util.Log
import com.hafidh.domain.common.WrapperResponse
import com.hafidh.domain.login.LoginRepository
import com.hafidh.domain.login.model.LoginDomain
import com.synrgy.data.common.DataMapper.loginDtoToDomain
import com.synrgy.data.login.dto.LoginReq
import com.synrgy.data.login.service.LoginService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(private val service: LoginService) : LoginRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Flow<WrapperResponse<LoginDomain>> {
        return flow {
            try {
                val req = LoginReq(email, password)
                val response = service.login(req)
                Log.d("LoginRepositoryImpl", "response: $response")
                if (response.token != null) {
                    val mapper = loginDtoToDomain(response)
                    emit(WrapperResponse.Success(mapper))
                } else {
                    emit(WrapperResponse.Empty)
                }

            } catch (e: Exception) {
                emit(WrapperResponse.Error(e.stackTraceToString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}