package com.example.myreceipeapp.data.remote

import com.example.myreceipeapp.data.remote.dto.Carts.Cart
import com.example.myreceipeapp.data.remote.dto.Carts.CartResponse
import com.example.myreceipeapp.data.remote.dto.Carts.CartWithUser
import com.example.myreceipeapp.data.remote.dto.User.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CartApiService(private val client: HttpClient) {
    suspend fun getAllCarts(): CartResponse {
        return client.get(urlString = "${KtorClient.CARTS}").body()
    }
    suspend fun getCartById(id: Int): Cart {
        return client.get(urlString = "${KtorClient.CARTS}/$id").body()
    }
    suspend fun getUserById(id: Int): CartWithUser {
        return client.get(urlString = "${KtorClient.USERS}/$id").body()
    }
}