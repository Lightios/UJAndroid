package com.example.bazadanych.model

data class ShoppingCart(
    val products: MutableList<Pair<Product, Int>> = mutableListOf(),
    val totalPrice: Double = 0.0
)