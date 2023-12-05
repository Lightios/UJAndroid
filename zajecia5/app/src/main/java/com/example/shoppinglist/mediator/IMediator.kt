package com.example.shoppinglist.mediator

import com.example.shoppinglist.data.Product

interface IMediator {
    fun selectProduct(productName: Product)
    fun getCurrentProduct(): Product
    fun getProductsList(): MutableMap<Product, Int>
    fun addProduct(product: Product)
    fun increaseQuantity(product: Product)
    fun decreaseQuantity(product: Product)
}

