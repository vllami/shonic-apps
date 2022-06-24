package com.synrgy.data.forgotpassword.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ForgotPasswordReq(
    @SerializedName("email")
    val email: String
)
