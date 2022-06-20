package com.hafidh.domain.forgotpassword.usecase

import com.hafidh.domain.forgotpassword.ForgotPasswordRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(private val repo: ForgotPasswordRepository) {
    suspend fun checkEmail(email: String) = repo.sendEmailRequest(email)
    suspend fun validateCode(email: String, code: Int) = repo.validateOTP(email, code)
    suspend fun newPassword(email: String, newPassword: String, token: String) =
        repo.changePassword(email, newPassword, token)
}