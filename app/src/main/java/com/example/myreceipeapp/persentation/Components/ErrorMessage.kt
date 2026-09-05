package com.example.myreceipeapp.persentation.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.myreceipeapp.persentation.screens.recipeDetail.RecipeDetailViewModel
import com.example.myreceipeapp.ui.theme.myOrange

@Composable
fun ErrorMessage(
    viewModel: ViewModel,
    errorMessage: String?,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorMessage ?: "",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = myOrange
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onRetry,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = myOrange,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Retry",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}