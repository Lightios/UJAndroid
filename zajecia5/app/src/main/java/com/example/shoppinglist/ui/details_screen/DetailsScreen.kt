package com.example.shoppinglist.ui.details_screen

// CartScreen.kt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.shoppinglist.mediator.IMediator
import com.example.shoppinglist.ui.Screen

@Composable
fun DetailsScreen(navController: NavController, mediator: IMediator) {
    var product = mediator.getCurrentProduct()

    Column {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier.clickable( onClick =  { navController.navigate(Screen.MarketScreen.route) } )
        )
        Text(text = product.name)
        Text(text = product.description)
        Text(text = product.price.toString())
        Button(onClick = { mediator.addProduct(product) }) {
            Text(text = "Add to cart")
        }

    }
}