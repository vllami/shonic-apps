package com.synrgy.data.mapper

import com.hafidh.domain.signup.model.SignUpDomain
import com.synrgy.data.signup.dto.SignUpResp

object DataMapper {
    fun signUpDataToDomain(signUpResp: SignUpResp): SignUpDomain = SignUpDomain(code = signUpResp.status, message = signUpResp.message)
}