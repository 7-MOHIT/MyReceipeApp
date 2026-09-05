package com.example.myreceipeapp.persentation.screens.recipeDetail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import coil.compose.AsyncImage
import com.example.myreceipeapp.data.remote.dto.RecipeDTO
import com.example.myreceipeapp.persentation.Components.DetailSection
import com.example.myreceipeapp.persentation.Components.ErrorMessage
import com.example.myreceipeapp.persentation.Components.LoadingIndicator
import com.example.myreceipeapp.persentation.Components.MyTopBar
import com.example.myreceipeapp.persentation.Components.StatItem
import com.example.myreceipeapp.persentation.Components.infoChip
import com.example.myreceipeapp.persentation.ViewModels.RecipeDetailViewModel
import com.example.myreceipeapp.ui.theme.myOrange

@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    onBack: () -> Unit,
    viewModel: RecipeDetailViewModel = viewModel()
) {
    LaunchedEffect(recipeId) {
        viewModel.fetchRecipeDetails(id = recipeId)
    }
    Scaffold(
        topBar = {
            MyTopBar(
                title = "RECIPE DETAILS",
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
                    onRetry = { viewModel.fetchRecipeDetails(recipeId) })

                viewModel.recipes != null -> {
                    RecipeDetailContent(details = viewModel.recipes!!)
                }

            }

        }

    }
}

@Composable
fun RecipeDetailContent(
    details: RecipeDTO,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = details.image,
            contentDescription = details.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(shape = RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop,
        )
        DetailSection(
            title = "Recipe Details",
            icon = Icons.Default.RemoveRedEye
        ) {
            Column(modifier = Modifier.fillMaxWidth())
            {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = details.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Row() {
                        infoChip(
                            label = details.cuisine,
                            icon = Icons.Default.Public
                        )
                        infoChip(
                            label = details.difficulty,
                            icon = Icons.Default.Star
                        )
                        infoChip(
                            label = details.mealType.firstOrNull() ?: "",
                            icon = Icons.Default.Bolt
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    Row() {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Rating : ${details.rating}",
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
                        Spacer(modifier = Modifier.width(9.dp))
                        Text(
                            text = "ReviewsCount : ${details.reviewCount}",
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
        DetailSection(title = "At a Glance", icon = Icons.Default.Timer) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatItem(
                    label = "prep",
                    icon = Icons.Default.Schedule,
                    value = "${details.prepTimeMinutes}m"
                )

                StatItem(
                    label = "Cook",
                    icon = Icons.Default.Whatshot,
                    value = "${details.cookTimeMinutes}m"
                )

                StatItem(
                    label = "Serves",
                    icon = Icons.Default.Egg,
                    value = "${details.servings}m"
                )

                StatItem(
                    label = "Calories",
                    icon = Icons.Default.LocalFireDepartment,
                    value = "${details.caloriesPerServing}"
                )

            }
        }
//        HorizontalDivider(
//            modifier = Modifier.height(12.dp),
//            thickness = 1.dp,
//            color = Color.Gray.copy(alpha = 0.2f)
//        )
        DetailSection(title = "Ingrediants", icon = Icons.Default.Restaurant) {
            details.ingredients.forEach { ingredient ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    // this box will be used as a bullet in front of each ingredient or text simple to say.
                    Box(
                        modifier = Modifier
                            .padding(top = 7.dp)
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(color = myOrange)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = ingredient,
                        color = Color.DarkGray.copy(alpha = 0.85f)
                    )

                }

            }
        }
        DetailSection(title = "Instructions", icon = Icons.AutoMirrored.Filled.MenuBook) {
            details.instructions.forEachIndexed { index, instruction ->
                Row(
                    modifier = Modifier.padding(vertical = 6.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(color = myOrange),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${index + 1}",
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = instruction,
                        color = Color.DarkGray.copy(alpha = 0.85f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}





