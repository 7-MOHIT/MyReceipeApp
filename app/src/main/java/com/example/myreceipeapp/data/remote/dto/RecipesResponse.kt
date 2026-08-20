package com.example.myreceipeapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipesResponse(
    val recipes : List<>,
    val total : Int,
    val skip : Int,
    val limit : Int
)