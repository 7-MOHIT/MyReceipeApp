package com.example.myreceipeapp.data.remote

import com.example.myreceipeapp.data.remote.dto.Recipes.AddRecipeRequest
import com.example.myreceipeapp.data.remote.dto.Recipes.RecipeDTO
import com.example.myreceipeapp.data.remote.dto.Recipes.RecipeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RecipeApiService(private val client: HttpClient) {
    //  we can call our base url and then add the endpoint to it ,
    //  or can call the thing directly which we have already made in the previous file
    suspend fun getAllRecipes(): RecipeResponse {
        return client.get(urlString = "${KtorClient.RECIPE}").body()
//        return client.get(urlString = "${KtorClient.BASE_URL}recipes").body()
    }

    suspend fun getRecipeById(id: Int): RecipeDTO {
        return client.get(urlString = "${KtorClient.BASE_URL}recipes/$id").body()
    }

    suspend fun addRecipe(request: AddRecipeRequest) {
        client.post(urlString = "${KtorClient.BASE_URL}recipes/add") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }
}
