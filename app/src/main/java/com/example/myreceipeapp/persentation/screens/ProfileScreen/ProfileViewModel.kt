package com.example.myreceipeapp.persentation.screens.ProfileScreen

import androidx.lifecycle.ViewModel
import com.example.myreceipeapp.domain.Repository.Auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val isLoggedOut: Boolean = false
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        val user = authRepository.getCurrentUser()
        _uiState.update {
            it.copy(
                name = user?.displayName ?: "User",
                email = user?.email ?: ""
            )
        }
    }

    fun logout() {
        authRepository.logout()
        _uiState.update { it.copy(isLoggedOut = true) }
    }
}