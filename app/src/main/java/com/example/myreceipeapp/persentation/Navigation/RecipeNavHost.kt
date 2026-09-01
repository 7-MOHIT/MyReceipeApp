package com.example.myreceipeapp.persentation.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myreceipeapp.persentation.screens.home.HomeScreen
import com.example.myreceipeapp.persentation.screens.recipeDetail.RecipeDetailScreen

@Composable
fun RecipeNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        composable<HomeRoute> {
            HomeScreen(onRecipeClick = { navController.navigate(RecipeDetailRoute(recipeId = id)) })
        }
        composable<RecipeDetailRoute> { backStackEntry ->
            val detailRoute = backStackEntry.toRoute<RecipeDetailRoute>()
            RecipeDetailScreen(
                recipeId = detailRoute.recipeId,
                onBack = { navController.popBackStack() })

        }
    }
}