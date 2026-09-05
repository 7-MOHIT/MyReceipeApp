package com.example.myreceipeapp.data.remote.dto.Products

import kotlinx.serialization.Serializable

@Serializable
data class Dimensions(
    val depth: Double,
    val height: Double,
    val width: Double
)