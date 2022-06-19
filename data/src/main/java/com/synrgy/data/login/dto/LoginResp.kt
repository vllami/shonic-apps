package com.synrgy.data.login.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LoginResp(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("token")
    val token: String?
)