package com.hafidh.domain.common

import com.hafidh.domain.forgotpassword.model.ForgotPasswordDomain

sealed class Event{
    object Init : Event()
    data class Success(val data: ForgotPasswordDomain) : Event()
    data class Error(val error: String) : Event()
    data class Loading(val isLoading: Boolean) : Event()
}