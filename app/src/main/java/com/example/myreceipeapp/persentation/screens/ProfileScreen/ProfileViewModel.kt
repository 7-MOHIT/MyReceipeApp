package com.example.myreceipeapp.persentation.screens.ProfileScreen

import androidx.lifecycle.ViewModel
import com.example.myreceipeapp.domain.Repository.Auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    fun logout() {
        authRepository.logout()
    }
}