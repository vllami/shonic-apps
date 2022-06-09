package com.synrgy.data.mapper

import com.hafidh.domain.signup.model.CompleteSignUpDomain
import com.hafidh.domain.signup.model.SignUpDomain
import com.synrgy.data.signup.dto.CompleteSignUpResp
import com.synrgy.data.signup.dto.SignUpResp

object DataMapper {
    fun signUpDataToDomain(signUpResp: SignUpResp): SignUpDomain =
        SignUpDomain(code = signUpResp.status, message = signUpResp.message)

    fun completeSignDtoToDomain(completeSignUpResp: CompleteSignUpResp): CompleteSignUpDomain {
        val data = completeSignUpResp.data?.let {
            it.email?.let { email ->
                it.fullName?.let { fullName ->
                    CompleteSignUpDomain(
                        email = email,
                        fullName = fullName,
                    )
                }
            }
        }
        return data ?: CompleteSignUpDomain(email = "", fullName = "")
    }
}