package com.example.myreceipeapp.data.repository.Auth

import com.example.myreceipeapp.domain.Repository.Auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signUp(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(
                email,
                password
            ).await()
            result.user?.let { Result.success(it) }
                ?: Result.failure(Exception("Sign up failed, user is null"))
        } catch (e: Exception) {
            Result.failure(
                e
            )
        }
    }

    override suspend fun login(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(
                email,
                password
            ).await()
            result.user?.let {
                Result.success(
                    it
                )
            }
                ?: Result.failure(
                    Exception(
                        "Login failed, user is null"
                    )
                )
        } catch (e: Exception) {
            Result.failure(
                e
            )
        }
    }

    override fun logout() = firebaseAuth.signOut()

    override fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser
}