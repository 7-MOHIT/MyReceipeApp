package com.example.myreceipeapp.data.remote.dto

data class RecipesResponse(
    val recipes : List<>,
    val total : Int,
    val skip : Int,
    val limit : Int
)