package com.example.myreceipeapp.persentation.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.myreceipeapp.data.remote.dto.Recipes.RecipeDTO
import com.example.myreceipeapp.persentation.Components.ErrorMessage
import com.example.myreceipeapp.persentation.Components.LoadingIndicator
import com.example.myreceipeapp.persentation.Components.SearchableTopAppBar
import com.example.myreceipeapp.persentation.Navigation.CartScreenRoute
import com.example.myreceipeapp.persentation.Navigation.ProductMainScreenRoute
import com.example.myreceipeapp.persentation.Navigation.ProfileScreenRoute
import com.example.myreceipeapp.ui.theme.myOrange
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


@Composable
fun DrawerTop(navController: NavController) {
    val userName = remember {
        FirebaseAuth.getInstance().currentUser?.displayName ?: "User"
    }
    Column() {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Text(
                text = userName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable(onClick = {
                        navController.navigate(
                            ProfileScreenRoute
                        )
                    })
                    .size(30.dp)
                    .clip(shape = CircleShape)
                    .background(color = myOrange),
                contentAlignment = Alignment.Center,

                ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(25.dp)
                )
            }
        }
        Text(
            text = "Dummy Json Data",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    onRecipeClick: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val isSearchActive = viewModel.isSearchActive
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()
    LaunchedEffect(
        viewModel.isSearchActive,
        viewModel.searchQuery
    ) {
        gridState.scrollToItem(0)
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.65f)) {
                Spacer(modifier = Modifier.height(12.dp))
                DrawerTop(navController);

                HorizontalDivider(modifier = Modifier.padding(bottom = 2.dp))

                NavigationDrawerItem(
                    label = {
                        Text(
                            "RECIPES",
                            color = myOrange
                        )
                    },
                    selected = true,
                    onClick = {
                        scope.launch { drawerState.close() }
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
    ) {
        Scaffold(
            topBar = {
                SearchableTopAppBar(
                    title = "RECIPES",
                    isSearchActive = viewModel.isSearchActive,
                    searchQuery = viewModel.searchQuery,
                    onSearchQueryChange = viewModel::onSearchQueryChange,
                    onSearchToggle = viewModel::onSearchToggle,
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            }
        ) { innerPadding ->
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
                        onRetry = { viewModel.fetchRecipes() }
                    )

                    else -> {
                        LazyVerticalGrid(
                            state = gridState,
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(
                                16.dp,
                            ),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            if (!isSearchActive) {
                                item(span = { GridItemSpan(currentLineSpan = maxLineSpan) }) {
                                    // will be shown always.

                                    HomeHeader()
                                }

                                if (viewModel.categories.size > 1) {
                                    //only if the no. of categories is greater than 1
                                    item(span = { GridItemSpan(currentLineSpan = maxLineSpan) }) {
                                        categorySection(
                                            category = viewModel.categories,
                                            selected = viewModel.selectedCategory,
                                            onSelected = viewModel::onCategorySelected
                                        )
                                    }
                                }
                                item(span = { GridItemSpan(currentLineSpan = maxLineSpan) }) {
                                    // this will be shown always.
                                    SectionHeader(
                                        title =
                                            if (viewModel.selectedCategory == "All") "All Recipes"
                                            else viewModel.selectedCategory,
                                        icon = Icons.Default.Menu
                                    )
                                }
                            }
                            if (viewModel.recipes.isEmpty()) {
                                item(span = { GridItemSpan(currentLineSpan = maxLineSpan) }) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 48.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "No Recipes Found",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = myOrange
                                        )
                                    }
                                }
                            } else {
                                items(viewModel.recipes, key = { it.id }) { recipe ->
                                    RecipeCard(
                                        recipe,
                                        onClick = {
                                            Log.d("msg", "moving to detail screen.")
                                            onRecipeClick(recipe.id)
                                        }
                                    )
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
fun RecipeCard(
    recipe: RecipeDTO,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = myOrange,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column() {
            //Image
            SubcomposeAsyncImage(
                model = recipe.image,
                contentDescription = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    ),
                contentScale = ContentScale.Crop,
                loading = { LoadingIndicator(2.dp) },
                error = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = "\uD83D\uDC7B",
                            fontSize = 32.sp
                        )
                    }
                }
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = recipe.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = recipe.cuisine,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .background(
                                color = myOrange.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = recipe.difficulty,
                            fontSize = 12.sp,
                            color = myOrange,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Rating : ${recipe.rating}",
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        Icons.Default.StarOutline,
                        contentDescription = "Star",
                        Modifier.size(15.dp)
                    )
                }
                Text(
                    text = "ReviesCount : ${recipe.reviewCount}",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }
    }
}

@Composable
fun categorySection(
    category: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    Column() {
        SectionHeader(
            title = "Categories",
            icon = Icons.Default.Restaurant
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(category) { category ->
                val isSelected = selected == category
                FilterChip(
                    selected = isSelected,
                    onClick = { onSelected(category) },
                    label = {
                        Text(
                            text = category,
                            fontWeight = if (isSelected) FontWeight.SemiBold
                            else FontWeight.Normal
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = myOrange,
                        selectedLabelColor = Color.White,
                        labelColor = Color.DarkGray,
                        containerColor = Color.White
                    ),
                    border = null
                )
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    icon: ImageVector,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = myOrange,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
    }

}

@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color = myOrange.copy(alpha = 0.1f))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(
                    color = myOrange,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.RestaurantMenu,
                contentDescription = "Restaurant Menu Icon",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column() {
            Text(
                text = "Hello, Chef!!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "Find something delicious to Cook")
        }
    }
}
