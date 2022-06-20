package com.synrgy.data.forgotpassword.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ValidateOtpReq(
    @SerializedName("email")
    val email: String?,
    @SerializedName("otp")
    val otp: Int?
)