package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.shoppinglist.ui.NavGraph
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import com.example.shoppinglist.data.Product

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph()
                }
            }
        }
    }
}

@Composable
fun ItemCard(product: Product, onClick: () -> Unit) {
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
                text = "${product.price} zł"
            )

            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                modifier = Modifier.clickable(onClick = onClick)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ShoppingListTheme {
//
//        ItemCard(name = "Hello", onClick = { })
//    }
//}