package com.example.myreceipeapp.data.repository

import com.example.myreceipeapp.data.remote.RecipeApiService
import com.example.myreceipeapp.data.remote.dto.RecipeDTO
import com.example.myreceipeapp.domain.Repository.RecipeRepository

class RecipeRepositoryImpl(private val apiService: RecipeApiService) : RecipeRepository {
    override suspend fun getAllRecipes(): List<RecipeDTO> {
        return apiService.getAllRecipes().recipes
    }
    override suspend fun getRecipeById(id: Int): RecipeDTO {
        return apiService.getRecipeById(id)
    }
}