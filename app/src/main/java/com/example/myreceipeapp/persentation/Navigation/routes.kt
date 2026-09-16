package com.example.myreceipeapp.persentation.Navigation

import kotlinx.serialization.Serializable

@Serializable
object SplashRoute

@Serializable
object LoginScreenRoute {

}
@Serializable
data class CartDetailScreenRoute(val cartId:Int){

}
@Serializable
object SignUpScreenRoute

@Serializable
object HomeRoute {

}
@Serializable
object ProfileScreenRoute

@Serializable
data class RecipeDetailRoute(val recipeId: Int) {

}

@Serializable
object ProductMainScreenRoute {

}

@Serializable
data class ProductDetailScreenRoute(val productId: Int) {

}

@Serializable
object CartScreenRoute

