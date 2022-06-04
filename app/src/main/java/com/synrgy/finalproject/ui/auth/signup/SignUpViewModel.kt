package com.synrgy.finalproject.ui.auth.signup

import androidx.lifecycle.ViewModel
import com.hafidh.domain.signup.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase) : ViewModel() {
}