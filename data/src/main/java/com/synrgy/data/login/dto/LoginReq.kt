package com.synrgy.data.login.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LoginReq(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?
)