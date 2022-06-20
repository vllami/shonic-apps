package com.synrgy.data.forgotpassword.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ValidateOtpResp(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("token")
    val token: String?
)