package com.example.myreceipeapp.domain.Repository.Products

import com.example.myreceipeapp.data.remote.dto.Products.Product

interface ProductRepository  {
    suspend fun getAllProducts() :  List<Product>
    suspend fun getProductsById(id:Int) : Product

}