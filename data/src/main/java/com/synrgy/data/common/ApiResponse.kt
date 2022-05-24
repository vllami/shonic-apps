package com.synrgy.data.common

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val exception: Exception) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}
