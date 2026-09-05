package com.example.myreceipeapp.domain.Repository.Recipes

import com.example.myreceipeapp.data.remote.dto.Recipes.RecipeDTO

interface RecipeRepository {
    // this file is required to tell all the methods and functions to fetch the data.


    // this is a interface class
    // so it will only tell us which functions will be used , and all these function will be defined in the DATA layer. itself .



    suspend fun getAllRecipes():List<RecipeDTO>
    suspend fun getRecipeById(id: Int): RecipeDTO
}