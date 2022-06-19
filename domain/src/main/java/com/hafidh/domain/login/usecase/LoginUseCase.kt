package com.hafidh.domain.login.usecase

import com.hafidh.domain.common.PreferenceProvider
import com.hafidh.domain.login.LoginRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val repository: LoginRepository,
    private val pref: PreferenceProvider
) {
    suspend fun login(email: String, password: String) = repository.login(email, password)
    fun saveToken(token: String) = pref.putString(TOKEN, token)

    companion object {
        const val TOKEN = "token"
    }

}