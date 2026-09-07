package com.example.myreceipeapp.domain.Repository.Carts

import com.example.myreceipeapp.data.remote.dto.Carts.Cart

interface CartRepository  {
    suspend fun getAllCarts():List<Cart>
}