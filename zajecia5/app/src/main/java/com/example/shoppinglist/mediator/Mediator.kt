package com.example.shoppinglist.mediator

import com.example.shoppinglist.data.Product
import com.example.shoppinglist.data.products

class Mediator : IMediator {
    private val productsList: MutableMap<Product, Int> = mutableMapOf()
    private var currentProduct: Product = products[0]

    override fun selectProduct(product: Product) {
        currentProduct = product
    }

    override fun getCurrentProduct(): Product {
        return currentProduct
    }

    override fun getProductsList(): MutableMap<Product, Int> {
        return productsList
    }

    override fun addProduct(product: Product) {
        if (productsList[product] == null) {
            productsList[product] = 1
        } else {
            productsList[product] = productsList[product]!! + 1
        }
    }

    override fun increaseQuantity(product: Product) {
        if (productsList[product] != null) {
            productsList[product] = productsList[product]!! + 1
        }
    }

    override fun decreaseQuantity(product: Product) {
        if (productsList[product] != null) {
            if (productsList[product]!! > 1) {
                productsList[product] = productsList[product]!! - 1
            } else {
                productsList.remove(product)

            }
        }
    }
}