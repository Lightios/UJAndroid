package com.example.dao

import com.example.models.*

interface DAOFacade {
    suspend fun allProducts(): List<Product>
    suspend fun product(id: Int): Product?
    suspend fun addNewProduct(name: String, price: Double, categoryId: Int): Product?
    suspend fun editProduct(id: Int, name: String, price: Double, categoryId: Int): Boolean
    suspend fun deleteProduct(id: Int): Boolean
}