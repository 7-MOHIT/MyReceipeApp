package com.example.myreceipeapp.persentation.screens.LogIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val generalError: String? = null,
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false
)

class LogInViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value, emailError = null, generalError = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, passwordError = null, generalError = null) }
    }

    fun login() {
        val state = _uiState.value
        val emailError = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
                state.email
            ).matches()
        ) {
            "Enter a valid email"
        } else {
            null
        }
        val passwordError = if (state.password.length < 6) {
            "Password must be at least 6 characters"
        } else {
            null
        }

        if (emailError != null || passwordError != null) {
            _uiState.update {
                it.copy(
                    emailError = emailError,
                    passwordError = passwordError
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1200)
            val loginWorked = true
            if (loginWorked) {
                _uiState.update { it.copy(isLoading = false, isLoginSuccessful = true) }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        generalError = "Invalid email or password"
                    )
                }
            }
        }
    }
}