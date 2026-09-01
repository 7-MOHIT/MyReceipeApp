package com.example.myreceipeapp.persentation.screens.recipeDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myreceipeapp.persentation.Components.ErrorMessage
import com.example.myreceipeapp.persentation.Components.LoadingIndicator
import com.example.myreceipeapp.persentation.ViewModels.RecipeDetailViewModel
import com.example.myreceipeapp.ui.theme.myOrange

@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    onBack: () -> Unit,
    viewModel: RecipeDetailViewModel = viewModel(),

    ) {

    LaunchedEffect(recipeId) {
        viewModel.fetchRecipeDetails(id = recipeId)
    }
    Scaffold(
        topBar = {
            MyTopBar(
                title = "RECIPE DETAILS",
                onBack,
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
                    onRetry = { viewModel.fetchRecipeDetails(recipeId) }
                )


            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    title: String, onBackClick: () -> Unit, icon: ImageVector
) {
    TopAppBar(
        title = {
            Text(
                text = title, fontWeight = FontWeight.Bold
            )
        }, navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = icon, contentDescription = "Back"
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.DarkGray,
            navigationIconContentColor = Color.DarkGray
        )
    )
}