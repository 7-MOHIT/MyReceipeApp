package com.example.myreceipeapp.data.repository

import com.example.myreceipeapp.data.remote.ProductApiService
import com.example.myreceipeapp.data.remote.dto.Products.Product
import com.example.myreceipeapp.domain.Repository.Products.ProductRepository

class ProductRepositoryImpl(private val apiService: ProductApiService): ProductRepository {
    override suspend fun getAllProducts(): List<Product> {
        return apiService.getAllProducts().products
    }

    override suspend fun getProductsById(id: Int): Product {
        return apiService.getProductById(id)
    }
}