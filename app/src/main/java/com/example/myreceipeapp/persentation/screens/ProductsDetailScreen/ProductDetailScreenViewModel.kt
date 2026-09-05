package com.example.myreceipeapp.persentation.screens.ProductsDetailScreen

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

class ProductDetailScreenViewModel : ViewModel() {
    private val repository: ProductRepository =
        ProductRepositoryImpl(apiService = ProductApiService(client = KtorClient.client))

    // if the data is loading then this will be in command
    var isLoading by mutableStateOf(false)
        private set

    // if some error comes, then it will be in command;
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var product by mutableStateOf<Product?>(null)
        private set

    fun fetchProductsById(productId: Int) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                viewModelScope.launch {
                    product = repository.getProductsById(productId)
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "An Unexpected error occured"
            } finally {
                isLoading = false
            }
        }
    }
}