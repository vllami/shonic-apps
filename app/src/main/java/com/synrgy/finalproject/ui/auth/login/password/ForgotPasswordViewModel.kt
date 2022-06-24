package com.synrgy.finalproject.ui.auth.login.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hafidh.domain.common.Event
import com.hafidh.domain.common.WrapperResponse
import com.hafidh.domain.forgotpassword.usecase.ForgotPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val useCase: ForgotPasswordUseCase) :
    ViewModel() {
    private val _stateCheckEmail = MutableStateFlow<Event>(Event.Init)
    val stateCheckEmail = _stateCheckEmail.asStateFlow()

    private val _stateValidate = MutableStateFlow<Event>(Event.Init)
    val stateValidate = _stateValidate.asStateFlow()

    private val _stateNewPassword = MutableStateFlow<Event>(Event.Init)
    val stateNewPassword = _stateNewPassword.asStateFlow()

    fun checkEmail(email: String) {
        viewModelScope.launch(Dispatchers.Main) {
            useCase.checkEmail(email)
                .onStart {
                    _stateCheckEmail.value = Event.Loading(true)
                }.catch { exception ->
                    _stateCheckEmail.value = Event.Loading(false)
                    _stateCheckEmail.value = Event.Error(exception.stackTraceToString())
                }.collect { result ->
                    when (result) {
                        is WrapperResponse.Success -> {
                            _stateCheckEmail.value = Event.Loading(false)
                            _stateCheckEmail.value = Event.Success(result.data)
                        }
                        is WrapperResponse.Empty -> Unit
                        is WrapperResponse.Error -> {
                            _stateCheckEmail.value = Event.Loading(false)
                            _stateCheckEmail.value = Event.Error(result.exception)
                        }
                    }
                }
        }
    }

    fun validate(email: String, code: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            useCase.validateCode(email, code)
                .onStart {
                    _stateValidate.value = Event.Loading(true)
                }.catch { exception ->
                    _stateValidate.value = Event.Loading(false)
                    _stateValidate.value = Event.Error(exception.stackTraceToString())
                }.collect { result ->
                    when (result) {
                        is WrapperResponse.Success -> {
                            _stateValidate.value = Event.Loading(false)
                            _stateValidate.value = Event.Success(result.data)
                        }
                        is WrapperResponse.Empty -> {
                            _stateValidate.value = Event.Loading(false)
                        }
                        is WrapperResponse.Error -> {
                            _stateValidate.value = Event.Loading(false)
                            _stateValidate.value = Event.Error(result.exception)
                        }
                    }

                }
        }
    }

    fun newPassword(email: String, newPassword: String, token: String) {
        viewModelScope.launch(Dispatchers.Main) {
            useCase.newPassword(email, newPassword, token)
                .onStart {
                    _stateNewPassword.value = Event.Loading(true)
                }.catch { exception ->
                    _stateNewPassword.value = Event.Loading(false)
                    _stateNewPassword.value = Event.Error(exception.stackTraceToString())
                }.onCompletion {
                    _stateNewPassword.value = Event.Loading(false)
                }.collect { result ->
                    when (result) {
                        is WrapperResponse.Success -> {
                            _stateNewPassword.value = Event.Loading(false)
                            _stateNewPassword.value = Event.Success(result.data)
                        }
                        is WrapperResponse.Empty -> {
                            _stateNewPassword.value = Event.Loading(false)
                        }
                        is WrapperResponse.Error -> {
                            _stateNewPassword.value = Event.Loading(false)
                            _stateNewPassword.value = Event.Error(result.exception)
                        }
                    }

                }
        }
    }

}