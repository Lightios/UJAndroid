package com.example.bazadanych.model

data class Product(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val category: Category = Category()
)