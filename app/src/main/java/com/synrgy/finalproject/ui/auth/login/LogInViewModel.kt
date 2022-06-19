package com.synrgy.finalproject.ui.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hafidh.domain.common.Event
import com.hafidh.domain.common.WrapperResponse
import com.hafidh.domain.login.model.LoginDomain
import com.hafidh.domain.login.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val useCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<Event<LoginDomain>>(Event.None)
    val loginState = _loginState.asStateFlow()
    fun login(email: String, password: String) {
        Log.d("LogInViewModel", email)
        Log.d("LogInViewModel", password)
        viewModelScope.launch(Dispatchers.IO) {
            useCase.login(email, password)
                .onStart {
                    _loginState.value = Event.Loading(true)
                }.onCompletion {
                    _loginState.value = Event.Loading(false)
                }.catch {
                    _loginState.value = Event.Loading(false)
                    _loginState.value = Event.Error(it.stackTraceToString())
                }.collect { result ->
                    when (result) {
                        is WrapperResponse.Success -> {
                            _loginState.value = Event.Loading(false)
                            result.data.token?.let { useCase.saveToken(it) }
                            _loginState.value = Event.Success(result.data)
                        }
                        is WrapperResponse.Error -> {
                            _loginState.value = Event.Loading(false)
                            _loginState.value = Event.Error(result.exception)
                        }

                        is WrapperResponse.Empty -> {
                            _loginState.value = Event.Loading(false)
                            _loginState.value = Event.None
                        }
                    }
                }
        }
    }
}