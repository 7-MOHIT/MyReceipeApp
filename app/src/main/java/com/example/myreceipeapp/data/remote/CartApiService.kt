package com.example.myreceipeapp.data.remote

import com.example.myreceipeapp.data.remote.dto.Carts.CartResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CartApiService(private val client: HttpClient) {
    suspend fun getAllCarts(): CartResponse {
        return client.get(urlString = "${KtorClient.CARTS}").body()
    }
}