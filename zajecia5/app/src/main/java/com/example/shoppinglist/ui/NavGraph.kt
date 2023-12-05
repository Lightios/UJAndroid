package com.example.shoppinglist.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.ui.market_screen.MarketScreen
import com.example.shoppinglist.ui.cart_screen.CartScreen
import com.example.shoppinglist.ui.details_screen.DetailsScreen
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import com.example.shoppinglist.mediator.Mediator


sealed class Screen(val route: String) {
    object MarketScreen : Screen("market_screen")
    object CartScreen : Screen("cart_screen")
    object DetailsScreen : Screen("details_screen")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val mediator = Mediator()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    label = { Text("Market") },
                    selected = navController.currentDestination?.hierarchy?.any { it.route == Screen.MarketScreen.route } == true,
                    onClick = {
                        navController.navigate(Screen.MarketScreen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
                    label = { Text("Cart") },
                    selected = navController.currentDestination?.hierarchy?.any { it.route == Screen.CartScreen.route } == true,
                    onClick = {
                        navController.navigate(Screen.CartScreen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.MarketScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.MarketScreen.route) {
                MarketScreen(navController = navController, mediator = mediator)
            }
            composable(Screen.CartScreen.route) {
                CartScreen(navController = navController, mediator = mediator)
            }
            composable(Screen.DetailsScreen.route) {
                DetailsScreen(navController = navController, mediator = mediator)
            }
        }
    }
}
