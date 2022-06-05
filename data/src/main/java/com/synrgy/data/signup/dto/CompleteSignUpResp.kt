package com.synrgy.data.signup.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CompleteSignUpResp(
    @SerializedName("data")
    val data: Data?,
    @SerializedName("error")
    val error: Any?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)