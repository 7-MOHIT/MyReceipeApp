package com.example.myreceipeapp.persentation.Navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myreceipeapp.persentation.screens.CartMainScreen.CartMainScreen
import com.example.myreceipeapp.persentation.screens.auth.LogIn.LogInScreen
import com.example.myreceipeapp.persentation.screens.ProductsDetailScreen.ProductDetailScreen
import com.example.myreceipeapp.persentation.screens.ProductsHome.ProductMainScreen
import com.example.myreceipeapp.persentation.screens.ProfileScreen.ProfileScreen
import com.example.myreceipeapp.persentation.screens.auth.SignUp.SignUpScreen
import com.example.myreceipeapp.persentation.screens.Splash.SplashScreen
import com.example.myreceipeapp.persentation.screens.auth.AuthViewModel
import com.example.myreceipeapp.persentation.screens.home.HomeScreen
import com.example.myreceipeapp.persentation.screens.recipeDetail.RecipeDetailScreen

@Composable
fun RecipeNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SplashRoute
    ) {
        composable<SplashRoute> {
            val authViewModel: AuthViewModel = hiltViewModel()
            SplashScreen(onTimeout = {
                val destination = if (authViewModel.isUserLoggedIn()) {
                    HomeRoute
                } else {
                    LoginScreenRoute
                }
                navController.navigate(destination) {
                    popUpTo(SplashRoute) { inclusive = true }
                }
            })
        }
        composable<HomeRoute> {
            HomeScreen(
                onRecipeClick = { id ->
                    navController.navigate(
                        RecipeDetailRoute(recipeId = id)
                    )
                },
                navController = navController
            )
        }
        composable<RecipeDetailRoute> { backStackEntry ->
            val detailRoute = backStackEntry.toRoute<RecipeDetailRoute>()
            RecipeDetailScreen(
                recipeId = detailRoute.recipeId,
                onBack = { navController.popBackStack() })
        }
        composable<ProductMainScreenRoute> {
            ProductMainScreen(
                onClick = { id ->
                    navController.navigate(
                        ProductDetailScreenRoute(id)
                    )
                },
                navController = navController
            )
        }
        composable<ProductDetailScreenRoute> { backStackEntry ->
            val detailRoute = backStackEntry.toRoute<ProductDetailScreenRoute>()
            ProductDetailScreen(
                detailRoute.productId,
                onBack = { navController.popBackStack() })
        }
        composable<CartScreenRoute> {
            CartMainScreen(navController = navController)
        }
        composable<LoginScreenRoute> {
            LogInScreen(
                onLoginSuccess = {
                    navController.navigate(HomeRoute) {
                        popUpTo(LoginScreenRoute) { inclusive = true }
                        popUpTo(SignUpScreenRoute) { inclusive = true }
                    }
                },
                onNavigateToSignUp = { navController.navigate(SignUpScreenRoute) })
        }
        composable<SignUpScreenRoute> {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(HomeRoute) {
                        popUpTo(LoginScreenRoute) { inclusive = true }
                        popUpTo(SignUpScreenRoute) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.navigate(LoginScreenRoute) })
        }
        composable<ProfileScreenRoute> {
            ProfileScreen(
                onLogout = {
                    navController.navigate(LoginScreenRoute) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                },
                navController = navController
            )
        }
    }
}