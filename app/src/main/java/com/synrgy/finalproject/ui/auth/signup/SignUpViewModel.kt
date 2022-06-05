package com.synrgy.finalproject.ui.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hafidh.domain.common.WrapperResponse
import com.hafidh.domain.signup.model.CompleteSignUpDomain
import com.hafidh.domain.signup.model.SignUpDomain
import com.hafidh.domain.signup.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    private val _stateCheckEmail = MutableStateFlow<SignUpState>(SignUpState.Init)
    val stateCheckEmail: StateFlow<SignUpState> = _stateCheckEmail

    private val _stateVerifyEmail = MutableStateFlow<SignUpState>(SignUpState.Init)
    val stateVerifyEmail: StateFlow<SignUpState> = _stateVerifyEmail

    private val _stateComplete = MutableStateFlow<CompleteSignUpState>(CompleteSignUpState.Init)
    val stateComplete: StateFlow<CompleteSignUpState> = _stateComplete

    fun checkEmail(email: String) {
        viewModelScope.launch(Dispatchers.Main) {
            signUpUseCase.checkEmail(email)
                .onStart {
                    _stateCheckEmail.value = SignUpState.Loading(true)
                }
                .catch { error ->
                    _stateCheckEmail.value = SignUpState.Loading(false)
                    _stateCheckEmail.value = SignUpState.Error(error.stackTraceToString())
                }
                .collect { result ->
                    _stateCheckEmail.value = SignUpState.Loading(false)
                    when (result) {
                        is WrapperResponse.Success -> {
                            _stateCheckEmail.value = SignUpState.Success(result.data)
                        }
                        is WrapperResponse.Error -> {
                            _stateCheckEmail.value = SignUpState.Error(result.exception)
                        }
                        is WrapperResponse.Empty -> {
                            _stateCheckEmail.value = SignUpState.Init
                        }
                    }
                }
        }
    }


    fun verifyEmail(email: String, otp: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            signUpUseCase.verifyEmail(email, otp)
                .onStart {
                    _stateVerifyEmail.value = SignUpState.Loading(true)
                }
                .catch { error ->
                    _stateVerifyEmail.value = SignUpState.Loading(false)
                    _stateVerifyEmail.value = SignUpState.Error(error.stackTraceToString())
                }
                .collect { result ->
                    _stateVerifyEmail.value = SignUpState.Loading(false)
                    when (result) {
                        is WrapperResponse.Success -> {
                            _stateVerifyEmail.value = SignUpState.Success(result.data)
                        }
                        is WrapperResponse.Error -> {
                            _stateVerifyEmail.value = SignUpState.Error(result.exception)
                        }
                        is WrapperResponse.Empty -> {
                            _stateVerifyEmail.value = SignUpState.Init
                        }
                    }
                }
        }
    }

    fun completeSignUp(email: String, fullName: String, password: String) {
        viewModelScope.launch(Dispatchers.Main) {
            signUpUseCase.completeRegister(email, fullName, password)
                .onStart {
                    _stateComplete.value = CompleteSignUpState.Loading(true)
                }
                .catch { error ->
                    _stateComplete.value = CompleteSignUpState.Loading(false)
                    _stateComplete.value = CompleteSignUpState.Error(error.stackTraceToString())
                }
                .collect { result ->
                    _stateComplete.value = CompleteSignUpState.Loading(false)
                    when (result) {
                        is WrapperResponse.Success -> {
                            _stateComplete.value = CompleteSignUpState.Success(result.data)
                        }
                        is WrapperResponse.Error -> {
                            _stateComplete.value = CompleteSignUpState.Error(result.exception)
                        }
                        is WrapperResponse.Empty -> {
                            _stateComplete.value = CompleteSignUpState.Init
                        }
                    }
                }
        }
    }

    sealed class SignUpState {
        object Init : SignUpState()
        data class Loading(val state: Boolean) : SignUpState()
        data class Success(val data: SignUpDomain) : SignUpState()
        data class Error(val message: String) : SignUpState()
    }

    sealed class CompleteSignUpState {
        object Init : CompleteSignUpState()
        data class Loading(val state: Boolean) : CompleteSignUpState()
        data class Success(val data: CompleteSignUpDomain) : CompleteSignUpState()
        data class Error(val message: String) : CompleteSignUpState()
    }
}