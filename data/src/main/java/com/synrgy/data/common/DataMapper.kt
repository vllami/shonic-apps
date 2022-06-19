package com.synrgy.data.common

import com.hafidh.domain.login.model.LoginDomain
import com.hafidh.domain.signup.model.CompleteSignUpDomain
import com.hafidh.domain.signup.model.SignUpDomain
import com.synrgy.data.login.dto.LoginResp
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

    fun loginDtoToDomain(loginResp: LoginResp): LoginDomain {
        val data = loginResp.token?.let {
            LoginDomain(token = it)
        }
        return data ?: LoginDomain(token = "")
    }
}