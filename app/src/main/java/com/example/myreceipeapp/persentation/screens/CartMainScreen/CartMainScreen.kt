package com.example.myreceipeapp.persentation.screens.CartMainScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.myreceipeapp.data.remote.dto.Carts.Cart
import com.example.myreceipeapp.persentation.Components.SearchableTopAppBar
import com.example.myreceipeapp.persentation.Navigation.CartDetailScreenRoute
import com.example.myreceipeapp.persentation.Navigation.CartScreenRoute
import com.example.myreceipeapp.persentation.Navigation.HomeRoute
import com.example.myreceipeapp.persentation.Navigation.ProductMainScreenRoute
import com.example.myreceipeapp.persentation.screens.home.DrawerTop
import com.example.myreceipeapp.ui.theme.myOrange
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartMainScreen(
    navController: NavController,
    viewModel: CartMainScreenViewModel = viewModel(),
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchCart(userId = 1)
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.65f)) {
                Spacer(modifier = Modifier.height(12.dp))
                DrawerTop(navController)
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
                    selected = false,
                    onClick = {
                        scope.launch {
                            navController.navigate(ProductMainScreenRoute)
                            drawerState.close()
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                NavigationDrawerItem(
                    label = {
                        Text(
                            "CARTS",
                            color = myOrange
                        )
                    },
                    selected = true,
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
    ) {
        Scaffold(
            topBar = {
                SearchableTopAppBar(
                    title = "CARTS",
                    isSearchActive = viewModel.isSearchActive,
                    searchQuery = viewModel.searchQuery,
                    onSearchQueryChange = viewModel::onSearchQueryChange,
                    onSearchToggle = viewModel::onSearchToggle,
                    onMenuClick = { scope.launch { drawerState.open() } })
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(
                    innerPadding
                )
            ) {


                when {
                    viewModel.isLoading -> Text("Loading...")
                    viewModel.errorMessage != null -> Text("Error: ${viewModel.errorMessage}")
                    else -> {
                        val carts = viewModel.cartItems
                        LazyColumn {
                            items(carts, key = { it.id }) { cart ->
                                CartCard(cart) { cartId ->
                                    navController.navigate(CartDetailScreenRoute(cartId = cartId))
                                }
                            }
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun CartCard(
    cart: Cart,
    onClick: (Int) -> Unit
) {
    Card(
        onClick = { onClick(cart.id) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = myOrange,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Cart #${cart.id}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Surface(
                    shape = RoundedCornerShape(50),
                    color = myOrange.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "$${cart.discountedTotal}",
                        color = myOrange,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "User ${cart.userId} · ${cart.totalProducts} items · Qty ${cart.totalQuantity}",
                    fontSize = 16.sp,
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.4f))
            Spacer(modifier = Modifier.height(10.dp))

            cart.products.forEach { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = product.thumbnail,
                        contentDescription = product.title,
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = product.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1
                        )
                        Text(
                            text = "$${product.price}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = Color.LightGray.copy(alpha = 0.3f)
                    ) {
                        Text(
                            text = "x${product.quantity}",
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }
    }
}