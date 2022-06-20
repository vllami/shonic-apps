package com.synrgy.finalproject.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hafidh.domain.common.WrapperResponse
import com.hafidh.domain.login.model.LoginDomain
import com.hafidh.domain.login.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val useCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Init)
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.Main) {
            useCase.login(email, password).onStart {
                _loginState.value = LoginState.Loading(true)
            }.catch {
                _loginState.value = LoginState.Error(it.stackTraceToString())
            }.collect { result ->
                when (result) {
                    is WrapperResponse.Empty -> Unit
                    is WrapperResponse.Error -> {
                        _loginState.value = LoginState.Loading(false)
                        _loginState.value = LoginState.Error(result.exception)
                    }
                    is WrapperResponse.Success -> {
                        _loginState.value = LoginState.Loading(false)
                        _loginState.value = LoginState.Success(result.data)
                        useCase.saveToken(result.data.token)
                    }
                }
            }
        }
    }

    sealed class LoginState {
        object Init : LoginState()
        data class Loading(val isloading: Boolean) : LoginState()
        data class Success(val user: LoginDomain) : LoginState()
        data class Error(val error: String) : LoginState()
    }
}