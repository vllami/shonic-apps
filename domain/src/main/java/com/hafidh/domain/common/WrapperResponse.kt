package com.hafidh.domain.common

sealed class WrapperResponse<out T> {
    data class Success<out T>(val data: T) : WrapperResponse<T>()
    data class Error(val exception: String) : WrapperResponse<Nothing>()
    object Empty : WrapperResponse<Nothing>()
}
