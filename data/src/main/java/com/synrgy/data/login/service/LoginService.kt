package com.synrgy.data.login.service

import com.synrgy.data.login.dto.LoginReq
import com.synrgy.data.login.dto.LoginResp
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginReq): LoginResp
}