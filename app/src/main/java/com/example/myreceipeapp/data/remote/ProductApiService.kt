package com.example.myreceipeapp.data.remote

import com.example.myreceipeapp.data.remote.dto.Products.Product
import com.example.myreceipeapp.data.remote.dto.Products.ProductsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ProductApiService(private val client: HttpClient) {
    suspend fun getAllProducts(): ProductsResponse{
        return client.get(urlString = "${KtorClient.PRODUCTS}").body()
    }
    suspend fun getProductById(id:Int): Product {
        return client.get(urlString = "${KtorClient.PRODUCTS}/$id").body()
    }
}