package com.example.myreceipeapp.persentation.screens.auth.SignUp

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myreceipeapp.domain.Repository.Auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val generalError: String? = null,
    val isLoading: Boolean = false,
    val isSignUpSuccessful: Boolean = false
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    fun onNameChange(value: String) {
        _uiState.update { it.copy(name = value, nameError = null, generalError = null) }
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value, emailError = null, generalError = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, passwordError = null, generalError = null) }
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.update {
            it.copy(
                confirmPassword = value,
                confirmPasswordError = null,
                generalError = null
            )
        }
    }

    fun signUp() {
        val state = _uiState.value

        val nameError = if (state.name.isBlank()) "Name is required" else null
        val emailError = if (!Patterns.EMAIL_ADDRESS.matcher(state.email).matches())
            "Enter a valid email" else null
        val passwordError = if (state.password.length < 6)
            "Password must be at least 6 characters" else null
        val confirmPasswordError = if (state.confirmPassword != state.password)
            "Passwords do not match" else null

        if (nameError != null || emailError != null || passwordError != null || confirmPasswordError != null) {
            _uiState.update {
                it.copy(
                    nameError = nameError,
                    emailError = emailError,
                    passwordError = passwordError,
                    confirmPasswordError = confirmPasswordError
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            authRepository.signUp(state.email, state.password)
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false, isSignUpSuccessful = true) }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            generalError = exception.message ?: "Sign up failed. Try again."
                        )
                    }
                }
        }
    }
}