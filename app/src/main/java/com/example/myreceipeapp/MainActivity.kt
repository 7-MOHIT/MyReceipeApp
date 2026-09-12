package com.example.myreceipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myreceipeapp.persentation.Navigation.RecipeNavHost
import com.example.myreceipeapp.ui.theme.MyReceipeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyReceipeAppTheme {
                RecipeNavHost()
            }
        }
    }
}
