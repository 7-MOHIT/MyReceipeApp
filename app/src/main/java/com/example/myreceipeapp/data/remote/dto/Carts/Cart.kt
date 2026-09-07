package com.example.myreceipeapp.data.remote.dto.Carts

import kotlinx.serialization.Serializable

@Serializable
data class Cart(
    val discountedTotal: Double,
    val id: Int,
    val products: List<Product>,
    val total: Double,
    val totalProducts: Int,
    val totalQuantity: Int,
    val userId: Int
)
@Serializable
data class CartResponse(
    val carts: List<Cart>,
    val limit: Int,
    val skip: Int,
    val total: Int
)
@Serializable
data class Product(
    val discountPercentage: Double,
    val discountedTotal: Double,
    val id: Int,
    val price: Double,
    val quantity: Int,
    val thumbnail: String,
    val title: String,
    val total: Double
)