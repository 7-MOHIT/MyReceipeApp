package com.example.myreceipeapp.persentation.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myreceipeapp.data.remote.KtorClient
import com.example.myreceipeapp.data.remote.RecipeApiService
import com.example.myreceipeapp.data.remote.dto.RecipeDTO
import com.example.myreceipeapp.data.repository.RecipeRepositoryImpl
import com.example.myreceipeapp.domain.RecipeRepository

class HomeViewModel : ViewModel() {
    private val repository: RecipeRepository =
        RecipeRepositoryImpl(
            apiService =
                RecipeApiService(KtorClient.client)
        )

    // if the data is loading then this will be in command
    var isLoading by mutableStateOf(false)
        private set

    // if some error comes, then it will be in command;
    var errorMessage by mutableStateOf<String?>(null)
        private set


    // now the recipes.. to fetch .. by default it is empty , but if data comes from server , then the data will be shown
    var recipes by mutableStateOf<List<RecipeDTO>>(emptyList())
        private set

    // user can select category , by default let me set it to ALL
    var selectedCategory by mutableStateOf("All")
        private set

    // to store all the recipes, if not a empty list will be shown.
    private var allRecipes :List<RecipeDTO>  = emptyList()



}