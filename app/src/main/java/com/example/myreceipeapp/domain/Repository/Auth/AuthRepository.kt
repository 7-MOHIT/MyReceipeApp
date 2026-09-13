package com.example.myreceipeapp.domain.Repository.Auth

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signUp(name: String, email: String, password: String): Result<FirebaseUser>
    suspend fun login(email: String, password: String): Result<FirebaseUser>
    fun logout()
    fun getCurrentUser(): FirebaseUser?
}