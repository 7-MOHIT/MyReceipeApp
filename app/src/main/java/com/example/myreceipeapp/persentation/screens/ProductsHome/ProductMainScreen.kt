package com.example.myreceipeapp.persentation.screens.ProductsHome

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myreceipeapp.data.remote.dto.Products.Product
import com.example.myreceipeapp.persentation.Components.ErrorMessage
import com.example.myreceipeapp.persentation.Components.LoadingIndicator
import com.example.myreceipeapp.persentation.Navigation.CartScreenRoute
import com.example.myreceipeapp.persentation.Navigation.HomeRoute
import com.example.myreceipeapp.persentation.Navigation.ProductMainScreenRoute
import com.example.myreceipeapp.persentation.screens.ProductsDetailScreen.TagsSection
import com.example.myreceipeapp.persentation.screens.home.SearchableTopAppBar
import com.example.myreceipeapp.ui.theme.myOrange
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductMainScreen(

    viewModel: ProductsMainScreenViewModel = viewModel(),
    onClick: (Int) -> Unit,
    navController: NavController,
) {
    val products = viewModel.products
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(250.dp)) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "DUMMY JSON DATA",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                HorizontalDivider(modifier = Modifier.padding(bottom = 2.dp))

                NavigationDrawerItem(
                    label = { Text("RECIPES") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            navController.navigate(HomeRoute)
                            drawerState.close()
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                NavigationDrawerItem(
                    label = { Text("PRODUCTS") },
                    selected = true,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                NavigationDrawerItem(
                    label = { Text("CARTS") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            navController.navigate(CartScreenRoute)
                            drawerState.close()
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )


            }
        }
    )

    {
        Scaffold(
            topBar = {
                SearchableTopAppBar(
                    title = "Products",
                    isSearchActive = viewModel.isSearchActive,
                    searchQuery = viewModel.searchQuery,
                    onSearchQueryChange = viewModel::onSearchQueryChange,
                    onSearchToggle = viewModel::onSearchToggle,
                    onMenuClick = { scope.launch { drawerState.open() } })
            }) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = myOrange.copy(alpha = 0.05f))
                    .padding(
                        innerPadding
                    )
            ) {
                when {
                    viewModel.isLoading -> LoadingIndicator(1.dp)
                    viewModel.errorMessage != null -> ErrorMessage(
                        errorMessage = viewModel.errorMessage,
                        viewModel = viewModel,
                        onRetry = { viewModel.fetchProducts() }
                    )

                    else -> {
                        LazyColumn()
                        {
                            items(products, key = { it.id }) { product ->
                                ProductItem(
                                    product = product,
                                    onClick = { onClick(product.id) }
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEFF5EF),
            contentColor = Color(0xFF1B5E20)
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
        ) {
            AsyncImage(
                model = product.thumbnail,
                contentDescription = product.title,
                modifier = Modifier
                    .size(126.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row() {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    product.brand?.let {
                        Text(
                            text = it.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    RatingRow(
                        rating = product.rating,
                        reviewCount = product.reviews.size
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    PriceRow(
                        price = product.price,
                        discountedPrice = product.discountedPrice,
                        discountPercentage = product.discountPercentage
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    TagsSection(product)
                }
                Column() {
                    StockBadge(product = product)
                }
            }
        }
    }
}


@Composable
private fun RatingRow(rating: Double, reviewCount: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector =
                Icons.Filled.Star,
            contentDescription = null,
            tint = Color(0xFFFFA000),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = "%.2f".format(rating),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "($reviewCount reviews)",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun PriceRow(price: Double, discountedPrice: Double, discountPercentage: Double) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$${"%.2f".format(discountedPrice)}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        if (discountPercentage > 0) {
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "$${"%.2f".format(price)}",
                style = MaterialTheme.typography.bodySmall,
                textDecoration = TextDecoration.LineThrough,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(6.dp))
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.errorContainer
            ) {
                Text(
                    text = "-${discountPercentage.roundToInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun StockBadge(product: Product) {
    val (text, color) = when {
        product.isOutOfStock -> "Out of stock" to MaterialTheme.colorScheme.error
        product.isLowStock -> "Only ${product.stock} left" to Color(0xFFF57C00)
        else -> "In stock" to Color(0xFF2E7D32)
    }
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = color,
        fontWeight = FontWeight.Medium
    )
}

