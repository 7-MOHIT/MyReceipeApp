package com.example.myreceipeapp.persentation.screens.ProductsHome

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myreceipeapp.data.remote.KtorClient
import com.example.myreceipeapp.data.remote.ProductApiService
import com.example.myreceipeapp.data.remote.dto.Products.Product
import com.example.myreceipeapp.data.repository.ProductRepositoryImpl
import com.example.myreceipeapp.domain.Repository.Products.ProductRepository
import kotlinx.coroutines.launch

class ProductsMainScreenViewModel : ViewModel() {
    private val repository: ProductRepository =
        ProductRepositoryImpl(apiService = ProductApiService(client = KtorClient.client))

    // if the data is loading then this will be in command
    var isLoading by mutableStateOf(false)
        private set

    // if some error comes, then it will be in command;
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // now the Pr0ducts .. to fetch .. by default it is empty , but if data comes from server
    //, then the data will be shown
    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    // to store all the recipes, if not a empty list will be shown.
    private var allProducts: List<Product> = emptyList()
    init {
        fetchProducts()
    }

    fun fetchProducts() {

        isLoading = true
        errorMessage = null
        viewModelScope.launch {
            try {
                val result = repository.getAllProducts()
                allProducts = result
                products = result
            } catch (e: Exception) {
                errorMessage = e.message ?: "An unexpected error occurred."
            } finally {//this will run in both the cases of try and catch.
                isLoading = false;
            }
        }
    }
}