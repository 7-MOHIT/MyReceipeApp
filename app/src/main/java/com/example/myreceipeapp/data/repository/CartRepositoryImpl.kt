package com.example.myreceipeapp.data.repository

import com.example.myreceipeapp.data.remote.CartApiService
import com.example.myreceipeapp.data.remote.dto.Carts.Cart
import com.example.myreceipeapp.domain.Repository.Carts.CartRepository

class CartRepositoryImpl(private val apiService: CartApiService) : CartRepository {
    override suspend fun getAllCarts(): List<Cart> {
        return apiService.getAllCarts().carts
    }
}