package com.example.myreceipeapp.domain.Repository.Carts

import com.example.myreceipeapp.data.remote.dto.Carts.Cart
import com.example.myreceipeapp.data.remote.dto.Carts.CartWithUser

interface CartRepository  {
    suspend fun getAllCarts():List<Cart>
    suspend fun getCartById(id: Int): Cart
    suspend fun getCartWithUser(cartId: Int): CartWithUser
}