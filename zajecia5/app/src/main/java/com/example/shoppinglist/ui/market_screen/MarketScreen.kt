package com.example.shoppinglist.ui.market_screen

// MarketScreen.kt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shoppinglist.ItemCard
import com.example.shoppinglist.data.products
import com.example.shoppinglist.mediator.IMediator
import com.example.shoppinglist.mediator.Mediator
import com.example.shoppinglist.ui.Screen
import com.example.shoppinglist.ui.cart_screen.CartScreen

@Composable
fun MarketScreen(navController: NavController, mediator: IMediator) {
    fun navigateToDetailsScreen() {
        navController.navigate(Screen.DetailsScreen.route)
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            Text(text = "Market")
        }
        items (products.count()) { i ->
            ItemCard(product = products[i], onClick = { navigateToDetailsScreen(); mediator.selectProduct(
                products[i]) })
        }
    }
}

@Preview
@Composable
fun MarketScreenPreview() {
    val mediator = Mediator()
    MarketScreen(navController = NavController(LocalContext.current), mediator = mediator)
}