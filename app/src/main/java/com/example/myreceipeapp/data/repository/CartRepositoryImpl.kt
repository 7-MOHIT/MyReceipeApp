package com.example.myreceipeapp.data.repository

import com.example.myreceipeapp.data.remote.CartApiService
import com.example.myreceipeapp.data.remote.dto.Carts.Cart
import com.example.myreceipeapp.data.remote.dto.Carts.CartWithUser
import com.example.myreceipeapp.domain.Repository.Carts.CartRepository

class CartRepositoryImpl(private val apiService: CartApiService) : CartRepository {
    override suspend fun getAllCarts(): List<Cart> {
        return apiService.getAllCarts().carts
    }

    override suspend fun getCartById(id: Int): Cart {
        return apiService.getCartById(id)
    }

    override suspend fun getCartWithUser(cartId: Int): CartWithUser {
        return apiService.getUserById(cartId)
    }
}