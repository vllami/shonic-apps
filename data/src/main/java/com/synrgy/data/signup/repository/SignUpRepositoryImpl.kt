package com.synrgy.data.signup.repository

import com.hafidh.domain.common.WrapperResponse
import com.hafidh.domain.signup.SignUpRepository
import com.hafidh.domain.signup.model.SignUpDomain
import com.synrgy.data.mapper.DataMapper.signUpDataToDomain
import com.synrgy.data.signup.dto.VerifyEmailReq
import com.synrgy.data.signup.service.SignUpService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpRepositoryImpl @Inject constructor(private val signUpService: SignUpService) :
    SignUpRepository {
    override suspend fun checkEmail(email: String): Flow<WrapperResponse<SignUpDomain>> {
        return flow {
            try {
                val response = signUpService.sendOtp(email)
                if (response.message == null) {
                    emit(WrapperResponse.Empty)
                } else {
                    val dataMapper = signUpDataToDomain(response)
                    emit(WrapperResponse.Success(dataMapper))
                }
            } catch (e: Exception) {
                emit(WrapperResponse.Error(e.stackTraceToString()))
            }
        }
    }

    override suspend fun verifyEmail(email: String, otp: Int): Flow<WrapperResponse<SignUpDomain>> {
        return flow {
            try {
                val req = VerifyEmailReq(email, otp)
                val response = signUpService.validateOtp(req)
                if (response.message == null) {
                    emit(WrapperResponse.Empty)
                } else {
                    val dataMapper = signUpDataToDomain(response)
                    emit(WrapperResponse.Success(dataMapper))
                }
            } catch (e: Exception) {
                emit(WrapperResponse.Error(e.stackTraceToString()))
            }
        }
    }
}