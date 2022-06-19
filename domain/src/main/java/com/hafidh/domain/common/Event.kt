package com.hafidh.domain.common

sealed class Event<out T> {
    data class Loading<T>(val isLoading: Boolean) : Event<T>()
    data class Success<T>(val data: T) : Event<T>()
    data class Error<T>(val error: String) : Event<T>()
    object None : Event<Nothing>()
}
