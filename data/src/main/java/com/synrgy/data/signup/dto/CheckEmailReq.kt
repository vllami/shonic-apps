package com.synrgy.data.signup.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckEmailReq(
    @SerializedName("email")
    val email: String
)
