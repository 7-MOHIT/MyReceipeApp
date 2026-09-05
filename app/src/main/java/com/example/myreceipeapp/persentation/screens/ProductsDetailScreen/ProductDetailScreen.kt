package com.example.myreceipeapp.persentation.screens.ProductsDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.myreceipeapp.data.remote.dto.Products.Product
import com.example.myreceipeapp.persentation.Components.ErrorMessage
import com.example.myreceipeapp.persentation.Components.LoadingIndicator
import com.example.myreceipeapp.persentation.Components.MyTopBar
import com.example.myreceipeapp.persentation.screens.ProductsHome.ProductsMainScreenViewModel
import com.example.myreceipeapp.persentation.screens.recipeDetail.RecipeDetailContent
import com.example.myreceipeapp.ui.theme.myOrange

@Composable
fun ProductDetailScreen(
    productId: Int,
    onBack: () -> Unit,
    viewModel: ProductDetailScreenViewModel = viewModel()
) {
    LaunchedEffect(productId) {
        viewModel.fetchProductsById(productId = productId)
    }
    Scaffold(
        topBar = {
            MyTopBar(
                title = "PRODUCT DETAILS",
                onBackClick = onBack,
                icon = Icons.AutoMirrored.Filled.ArrowBack
            )
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = myOrange.copy(alpha = 0.05f))

        ) {
            when {
                viewModel.isLoading -> LoadingIndicator(1.dp)
                viewModel.errorMessage != null -> ErrorMessage(
                    viewModel = viewModel,
                    errorMessage = viewModel.errorMessage,
                    onRetry = { viewModel.fetchProductsById(productId) })

                viewModel.product != null -> {
                    Text(text =
                    "${viewModel.product!!.title}")
//                    ProductDetailContent(product = viewModel.product!!)
                }
            }
        }
    }
}

@Composable
fun ProductDetailContent(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = product.images,
            contentDescription = product.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(shape = RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop,
        )
    }
}