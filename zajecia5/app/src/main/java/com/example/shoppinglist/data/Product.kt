package com.example.shoppinglist.data

data class Product (
    val name: String,
    val price: Double,
    val description: String
)

var products = listOf(
    Product("Milk", 2.5, "Milk description"),
    Product("Bread", 3.5, "Bread description"),
    Product("Butter", 4.5, "Butter description"),
    Product("Cheese", 5.5, "Cheese description"),
)