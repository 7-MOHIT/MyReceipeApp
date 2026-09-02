package com.example.myreceipeapp.persentation.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myreceipeapp.data.remote.KtorClient
import com.example.myreceipeapp.data.remote.RecipeApiService
import com.example.myreceipeapp.data.remote.dto.RecipeDTO
import com.example.myreceipeapp.data.repository.RecipeRepositoryImpl
import com.example.myreceipeapp.domain.Repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeDetailViewModel : ViewModel() {
    private val repository: RecipeRepository = RecipeRepositoryImpl(
        apiService = RecipeApiService(
            KtorClient.client
        )
    )

    // if the data is loading then this will be in command
    var isLoading by mutableStateOf(false)
        private set

    // if some error comes, then it will be in command;
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // now the recipes.. to fetch .. by default it is empty , but if data comes from server , then the data will be shown
    var recipes by mutableStateOf<RecipeDTO?>(null)
        private set

    // function to fetching the recipe by only a id given to it.
//    it will show all the details of the ... recipe whose id is passed to it.
    fun fetchRecipeDetails(id: Int) {
        viewModelScope.launch {
        isLoading = true
        errorMessage = null
        try {
            viewModelScope.launch {
                recipes = repository.getRecipeById(id)
            }
        } catch (e: Exception) {
            errorMessage = e.message ?: "An Unexpected error occured"
        } finally {
            isLoading = false
        }
    }
}
}