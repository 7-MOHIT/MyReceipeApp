package com.example.myreceipeapp.persentation.Navigation

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute {

}

@Serializable
data class RecipeDetailRoute(val recipeId: Int) {

}

@Serializable
object ProductMainScreenRoute{

}
