package com.example.bazadanych

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.bazadanych.model.Category
import com.example.bazadanych.model.Product
import com.example.bazadanych.ui.theme.BazaDanychTheme
import com.google.firebase.FirebaseApp

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*


suspend fun fetchProducts(): MutableList<Product> {
    return withContext(Dispatchers.IO) {
        val url = URL("http://10.0.2.2:8080/product")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val reader = BufferedReader(InputStreamReader(connection.inputStream))
        val response = reader.readText()
        reader.close()

        val listType = object : TypeToken<List<Product>>() {}.type
        Gson().fromJson(response, listType)
    }
}

suspend fun fetchCategories(): MutableList<Category> {
    return withContext(Dispatchers.IO) {
        val url = URL("http://10.0.2.2:8080/category")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val reader = BufferedReader(InputStreamReader(connection.inputStream))
        val response = reader.readText()
        reader.close()

        val listType = object : TypeToken<List<Category>>() {}.type
        Gson().fromJson(response, listType)
    }
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val products = fetchProducts()
            val categories = fetchCategories()

            setContent {
                BazaDanychTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column{
                            Text("Products")
                            ProductList(products)

                            Text("Categories")
                            CategoryList(categories)
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun ProductList(products: List<Product>) {
    LazyColumn {
        items(products) { product ->
            ProductItem(product)
        }
    }
}


@Composable
fun CategoryList(categories: List<Category>) {
    LazyColumn {
        items(categories) { category ->
            CategoryItem(category)
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
    Column()
    {
        Text(text = "Category Name: ${category.name}")
    }
}

@Composable
fun ProductItem(product: Product) {
    Column()
    {
        Text(text = "Product Name: ${product.name}")
        Text(text = "Description: ${product.description}")
        Text(text = "Price: ${product.price}")
    }
}

