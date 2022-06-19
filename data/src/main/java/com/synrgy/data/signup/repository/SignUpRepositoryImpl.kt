package com.synrgy.data.signup.repository

import com.hafidh.domain.common.WrapperResponse
import com.hafidh.domain.signup.SignUpRepository
import com.hafidh.domain.signup.model.CompleteSignUpDomain
import com.hafidh.domain.signup.model.SignUpDomain
import com.synrgy.data.common.DataMapper.completeSignDtoToDomain
import com.synrgy.data.common.DataMapper.signUpDataToDomain
import com.synrgy.data.signup.dto.CheckEmailReq
import com.synrgy.data.signup.dto.CompleteSignUpReq
import com.synrgy.data.signup.dto.VerifyEmailReq
import com.synrgy.data.signup.service.SignUpService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpRepositoryImpl @Inject constructor(private val signUpService: SignUpService) :
    SignUpRepository {
    override suspend fun checkEmail(email: String): Flow<WrapperResponse<SignUpDomain>> {
        return flow {
            try {
                val emailReq = CheckEmailReq(email)
                val response = signUpService.sendOtp(emailReq)
                if (response.status == 200) {
                    val dataMapper = signUpDataToDomain(response)
                    emit(WrapperResponse.Success(dataMapper))
                } else {
                    emit(WrapperResponse.Empty)
                }
            } catch (e: Exception) {
                emit(WrapperResponse.Error(e.stackTraceToString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun verifyEmail(email: String, otp: Int): Flow<WrapperResponse<SignUpDomain>> {
        return flow {
            try {
                val req = VerifyEmailReq(email, otp)
                val response = signUpService.validateOtp(req)
                if (response.status == 400) {
                    emit(WrapperResponse.Empty)
                } else {
                    val dataMapper = signUpDataToDomain(response)
                    emit(WrapperResponse.Success(dataMapper))
                }
            } catch (e: Exception) {
                emit(WrapperResponse.Error(e.stackTraceToString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun completeRegister(
        email: String,
        fullName: String,
        password: String
    ): Flow<WrapperResponse<CompleteSignUpDomain>> {
        return flow {
            try {
                val req = CompleteSignUpReq(email, fullName, password)
                val response = signUpService.signUp(req)
                if (response.status != 200) {
                    emit(WrapperResponse.Empty)
                } else {
                    val dataMapper = completeSignDtoToDomain(response)
                    emit(WrapperResponse.Success(dataMapper))
                }
            } catch (e: Exception) {
                emit(WrapperResponse.Error(e.stackTraceToString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}