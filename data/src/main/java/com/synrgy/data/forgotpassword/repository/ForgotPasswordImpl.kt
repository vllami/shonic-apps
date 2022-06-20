package com.synrgy.data.forgotpassword.repository

import com.hafidh.domain.common.WrapperResponse
import com.hafidh.domain.forgotpassword.ForgotPasswordRepository
import com.hafidh.domain.forgotpassword.model.ForgotPasswordDomain
import com.synrgy.data.forgotpassword.dto.NewpasswordReq
import com.synrgy.data.forgotpassword.dto.ValidateOtpReq
import com.synrgy.data.forgotpassword.service.ForgotPasswordService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ForgotPasswordImpl @Inject constructor(private val service: ForgotPasswordService) :
    ForgotPasswordRepository {
    override suspend fun sendEmailRequest(email: String): Flow<WrapperResponse<ForgotPasswordDomain>> {
        return flow {
            try {
                val response = service.sendForgotPasswordEmail(email)
                when (response.status) {
                    400 -> {
                        emit(WrapperResponse.Error(response.message ?: "Error"))
                    }
                    200 -> {
                        val mapper = ForgotPasswordDomain(
                            message = response.message,
                            status = response.status,
                            token = ""
                        )
                        emit(WrapperResponse.Success(mapper))
                    }
                    else -> {
                        emit(WrapperResponse.Empty)
                    }
                }

            } catch (e: Exception) {
                emit(WrapperResponse.Error(e.stackTraceToString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun validateOTP(
        email: String,
        otp: Int
    ): Flow<WrapperResponse<ForgotPasswordDomain>> {
        return flow {
            try {
                val mapper = ValidateOtpReq(
                    email = email,
                    otp = otp
                )
                val response = service.validateForgotPassword(mapper)
                if (response.status != 200) {
                    emit(WrapperResponse.Error("Something error"))
                } else {
                    val mapperToDomain = ForgotPasswordDomain(
                        message = response.message,
                        status = response.status,
                        token = response.token
                    )
                    emit(WrapperResponse.Success(mapperToDomain))
                }
            } catch (e: Exception) {
                emit(WrapperResponse.Error(e.stackTraceToString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun changePassword(
        email: String,
        newPassword: String,
        token: String
    ): Flow<WrapperResponse<ForgotPasswordDomain>> {
        return flow {
            try {
                val mapperReq = NewpasswordReq(
                    email = email,
                    newPassword = newPassword,
                    token = token
                )
                val response = service.resetPassword(mapperReq)
                if (response.status == 200) {
                    val mapperToDomain = ForgotPasswordDomain(
                        message = response.message,
                        status = response.status,
                        token = ""
                    )
                    emit(WrapperResponse.Success(mapperToDomain))
                } else {
                    emit(WrapperResponse.Error("Something error"))
                }
            } catch (e: Exception) {
                emit(WrapperResponse.Error(e.stackTraceToString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}