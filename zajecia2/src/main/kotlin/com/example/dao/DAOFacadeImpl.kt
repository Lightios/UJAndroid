package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : DAOFacade {
    private fun resultRowToProduct(row: ResultRow) = Product(
        id = row[Products.id],
        name = row[Products.name],
        price = row[Products.price],
        categoryId = row[Products.categoryId],
    )

    override suspend fun allProducts(): List<Product> = dbQuery {
        Products.selectAll().map(::resultRowToProduct)
    }

    override suspend fun product(id: Int): Product? = dbQuery {
        Products
            .select { Products.id eq id }
            .map(::resultRowToProduct)
            .singleOrNull()
    }

    override suspend fun addNewProduct(name: String, price: Double, categoryId: Int): Product? = dbQuery {
        val insertStatement = Products.insert {
            it[Products.name] = name
            it[Products.price] = price
            it[Products.categoryId] = categoryId
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToProduct)
    }

    override suspend fun editProduct(id: Int, name: String, price: Double, categoryId: Int): Boolean = dbQuery {
        Products.update({ Products.id eq id }) {
            it[Products.name] = name
            it[Products.price] = price
            it[Products.categoryId] = categoryId
        } > 0
    }

    override suspend fun deleteProduct(id: Int): Boolean = dbQuery {
        Products.deleteWhere { Products.id eq id } > 0
    }
}

val dao: DAOFacade = DAOFacadeImpl().apply {
    runBlocking {
        if(allProducts().isEmpty()) {
            addNewProduct("The drive to develop!", 0.2, 1)
        }
    }
}