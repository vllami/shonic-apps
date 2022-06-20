package com.synrgy.data.forgotpassword.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class NewpasswordReq(
    @SerializedName("email")
    val email: String?,
    @SerializedName("newPassword")
    val newPassword: String?,
    @SerializedName("token")
    val token: String?
)