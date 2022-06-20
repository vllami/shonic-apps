package com.synrgy.data.forgotpassword.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ForgotPasswordResp(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)