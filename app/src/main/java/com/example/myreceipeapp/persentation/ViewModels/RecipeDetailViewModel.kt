package com.example.myreceipeapp.persentation.ViewModels

import androidx.lifecycle.ViewModel
import com.example.myreceipeapp.data.remote.KtorClient
import com.example.myreceipeapp.data.remote.RecipeApiService
import com.example.myreceipeapp.data.repository.RecipeRepositoryImpl
import com.example.myreceipeapp.domain.Repository.RecipeRepository

class RecipeDetailViewModel : ViewModel() {
    private val repository: RecipeRepository = RecipeRepositoryImpl(
        apiService = RecipeApiService(
            KtorClient.client
        )
    )
}