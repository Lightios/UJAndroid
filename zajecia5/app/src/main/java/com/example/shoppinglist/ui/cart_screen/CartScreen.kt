package com.example.shoppinglist.ui.cart_screen

// CartScreen.kt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.shoppinglist.data.Product
import com.example.shoppinglist.mediator.IMediator
import com.example.shoppinglist.mediator.Mediator
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableIntStateOf

@Composable
fun CartScreen(navController: NavController, mediator: IMediator) {
    val products = mediator.getProductsList()
    var cards by remember { mutableStateOf(products.toList()) }

    fun recomposeCards(){
        cards = mediator.getProductsList().toList()
    }

    LazyColumn {
        item{
            Text(text = "Cart")
        }

        items(cards){product ->
            CartItem(product = product.first, quantity=product.second, mediator, recompose = { recomposeCards() })
        }
    }
}


@Preview
@Composable
fun CartScreenPreview() {
    val mediator = Mediator()
    CartScreen(navController = NavController(LocalContext.current), mediator = mediator)
}

@Composable
fun CartItem(product: Product, quantity: Int, mediator: IMediator, recompose: () -> Unit = {}) {
    var text by remember { mutableIntStateOf(quantity) }

    Card(
        modifier = Modifier.fillMaxWidth(),

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        )
        {
            Text(
                text = product.name,
            )

            Text(
                text = text.toString()
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier.clickable(onClick = { mediator.decreaseQuantity(product); text -= 1; recompose() })
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.clickable(onClick = { mediator.increaseQuantity(product); text += 1 })
            )
        }
    }
}