package com.hafidh.domain.signup.usecase

import com.hafidh.domain.signup.SignUpRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpUseCase @Inject constructor(private val signUpRepository: SignUpRepository) {
    suspend fun checkEmail(email: String) = signUpRepository.checkEmail(email)
    suspend fun verifyEmail(email: String, otp: Int) = signUpRepository.verifyEmail(email, otp)
}