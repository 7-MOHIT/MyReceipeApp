package com.example.myreceipeapp.persentation.screens.CartDetailScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myreceipeapp.data.remote.CartApiService
import com.example.myreceipeapp.data.remote.KtorClient
import com.example.myreceipeapp.data.remote.dto.Carts.Cart
import com.example.myreceipeapp.data.repository.CartRepositoryImpl
import com.example.myreceipeapp.domain.Repository.Carts.CartRepository
import kotlinx.coroutines.launch

class CartDetailScreenViewModel : ViewModel() {
    private val repository: CartRepository =
        CartRepositoryImpl(apiService = CartApiService(KtorClient.client))

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var cart by mutableStateOf<Cart?>(null)
        private set

    fun fetchCartById(cartId: Int) {
        isLoading = true
        errorMessage = null
        viewModelScope.launch {
            try {
                cart = repository.getCartById(cartId)
            } catch (e: Exception) {
                errorMessage = e.message ?: "An unexpected error occurred."
            } finally {
                isLoading = false
            }
        }
    }
}