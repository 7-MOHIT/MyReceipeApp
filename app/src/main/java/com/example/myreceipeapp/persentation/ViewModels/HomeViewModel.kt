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

    // list of all the categories.
    var categories by mutableStateOf<List<String>>(listOf("All"))
        private set

    // user can select category , by default let me set it to ALL
    var selectedCategory by mutableStateOf("All")
        private set

    // to store all the recipes, if not a empty list will be shown.
    private var allRecipes: List<RecipeDTO> = emptyList()

    fun fetchRecipes() {
        isLoading = true
        errorMessage = null
        viewModelScope.launch {
            try {
                val result = repository.getAllRecipes()
                allRecipes = result
                // NOW IF I WANT TO ADD CATEGORIES FOR THe RECIPE ITEMS...
//            like italian food  ,chinese food , then  we will add cuisine
                val cuisines = result.map { it.cuisine }.distinct().sorted()
                categories = listOf("All") + cuisines
                applyFilters()
            } catch (e: Exception) {
                errorMessage = e.message ?: "An unexpected error occurred."
            }
            finally {
                // this will run in both the cases.
                isLoading = false
            }
        }
    }

    // now if i want to show  recipes according to the category which user selects.
    private fun applyFilters() {
        recipes =
            if (selectedCategory == "All") allRecipes
            else allRecipes.filter { it.cuisine == selectedCategory }
    }
}
