package com.example.myreceipeapp.persentation.screens.CartDetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myreceipeapp.ui.theme.myOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDetailScreen(
    navController: NavController,
    cartId: Int,
    viewModel: CartDetailScreenViewModel = viewModel(),
) {
    LaunchedEffect(cartId) {
        viewModel.fetchCartById(cartId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cart #$cartId") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                viewModel.isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                viewModel.errorMessage != null -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: ${viewModel.errorMessage}")
                    }
                }
                viewModel.cart != null -> {
                    val cart = viewModel.cart!!
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
                        Spacer(Modifier.width(6.dp))
                        Text("User ${cart.userId}", fontSize = 14.sp, color = Color.Gray)
                        Spacer(Modifier.weight(1f))
                        Text(
                            "${cart.totalProducts} items · Qty ${cart.totalQuantity}",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                    HorizontalDivider()
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(cart.products, key = { it.id }) { product ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = product.thumbnail,
                                    contentDescription = product.title,
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(RoundedCornerShape(10.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(Modifier.width(12.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(
                                        product.title,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 15.sp
                                    )
                                    Text(
                                        "$${product.price} x ${product.quantity}",
                                        fontSize = 13.sp,
                                        color = Color.Gray
                                    )
                                }
                                Text(
                                    "$${product.discountedTotal}",
                                    fontWeight = FontWeight.SemiBold,
                                    color = myOrange
                                )
                            }
                        }
                    }
                    HorizontalDivider()
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text("Subtotal", color = Color.Gray)
                            Spacer(Modifier.weight(1f))
                            Text("$${cart.total}")
                        }
                        Spacer(Modifier.height(4.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text("Total (after discount)", fontWeight = FontWeight.Bold)
                            Spacer(Modifier.weight(1f))
                            Text(
                                "$${cart.discountedTotal}",
                                fontWeight = FontWeight.Bold,
                                color = myOrange,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}