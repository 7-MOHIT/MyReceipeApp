package com.example.myreceipeapp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }
    private const val BASE_URL = "https://dummyjson.com/"
    const val PRODUCTS = "$BASE_URL/products"
    const val CARTS = "$BASE_URL/carts"
    const val USERS = "$BASE_URL/users"
    const val POSTS = "$BASE_URL/posts"
    const val COMMENTS = "$BASE_URL/comments"
    const val QUOTES = "$BASE_URL/quotes"
    const val TODOS= "$BASE_URL/todos"
    const val RECIPE = "$BASE_URL/recipes"
    const val IMAGE = "$BASE_URL/images"
    const val AUTH = "$BASE_URL/auth"


}