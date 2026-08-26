package com.example.myreceipeapp.data.remote

import com.example.myreceipeapp.data.remote.dto.RecipeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RecipeApiService(private val client : HttpClient) {
    suspend fun getAllRecipes(): RecipeResponse{
        return client.get(urlString = "${KtorClient.BASE_URL}recipes").body()
    }
}