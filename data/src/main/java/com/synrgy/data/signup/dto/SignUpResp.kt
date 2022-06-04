package com.synrgy.data.signup.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SignUpResp(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)