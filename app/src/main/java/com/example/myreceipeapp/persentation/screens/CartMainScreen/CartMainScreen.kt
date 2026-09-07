package com.example.myreceipeapp.persentation.screens.CartMainScreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myreceipeapp.persentation.Navigation.CartScreenRoute
import com.example.myreceipeapp.persentation.Navigation.HomeRoute
import com.example.myreceipeapp.persentation.Navigation.ProductMainScreenRoute
import com.example.myreceipeapp.persentation.screens.home.SearchableTopAppBar
import kotlinx.coroutines.launch

@Composable
fun CartMainScreen(
    navController: NavController,
    viewModel: CartMainScreenViewModel = viewModel(),

    ) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(250.dp)) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "DUMMY JSON DATA",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                HorizontalDivider(modifier = Modifier.padding(bottom = 2.dp))

                NavigationDrawerItem(
                    label = { Text("RECIPES") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            navController.navigate(HomeRoute)
                            drawerState.close()
                        }
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
                    selected = true,
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

    }

}