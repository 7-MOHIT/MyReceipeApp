package com.example.myreceipeapp.persentation.screens.CartMainScreen

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

class CartMainScreenViewModel : ViewModel() {
    private val repository: CartRepository =
        CartRepositoryImpl(
            apiService = CartApiService(KtorClient.client)
        )

    // if the data is loading then this will be in command
    var isLoading by mutableStateOf(false)
        private set

    // if some error comes, then it will be in command;
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var cartItems by mutableStateOf<List<Cart>>(emptyList())
        private set


    fun fetchCart(userId: Int) {
        isLoading = true
        errorMessage = null
        viewModelScope.launch {
            try {
                cartItems = repository.getAllCarts()
            } catch (e: Exception) {
                errorMessage = e.message ?: "An unexpected error occurred."
            } finally {
                isLoading = false
            }
        }
    }
}