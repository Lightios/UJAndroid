package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

@Serializable
data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val categoryId: Int,
)

object Products : Table()
{
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val price = double("price")
    val categoryId = integer("categoryId")

    override val primaryKey = PrimaryKey(id)
}

val productStorage = mutableListOf<Product>(
    Product(1, "Chocolate", 10.2, 1),
    Product(2, "Meat", 15.2, 2)
)
