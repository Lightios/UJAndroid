package com.example.routes

import com.example.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.dao.*
import io.ktor.server.freemarker.*
import io.ktor.server.util.*

fun Route.productRouting() {

    route("/product") {
        get {
            call.respond(FreeMarkerContent("index.ftl", mapOf("products" to dao.allProducts())))
        }
        post {
            val formParameters = call.receiveParameters()
            val name = formParameters.getOrFail("name")
            val price = formParameters.getOrFail("price").toDouble()
            val categoryId = formParameters.getOrFail("categoryId").toInt()
            val product = dao.addNewProduct(name, price, categoryId)
            call.respondRedirect("/products/${product?.id}")
        }
        get("{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("show.ftl", mapOf("article" to dao.product(id))))

        }
        get("{id}/edit") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("edit.ftl", mapOf("article" to dao.product(id))))
        }
        post("{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val formParameters = call.receiveParameters()
            when (formParameters.getOrFail("_action")) {
                "update" -> {
                    val name = formParameters.getOrFail("name")
                    val price = formParameters.getOrFail("price").toDouble()
                    val categoryId = formParameters.getOrFail("categoryId").toInt()
                    dao.editProduct(id, name, price, categoryId)
                    call.respondRedirect("/products/$id")
                }
                "delete" -> {
                    dao.deleteProduct(id)
                    call.respondRedirect("/products")
                }
            }
        }

        // kod sprzed dodania bazy danych
//        get("{id?}") {
//            val id = call.parameters["id"] ?: return@get call.respondText(
//                "Missing id",
//                status = HttpStatusCode.BadRequest
//            )
//            val product =
//                productStorage.find { it.id == id } ?: return@get call.respondText(
//                    "No customer with id $id",
//                    status = HttpStatusCode.NotFound
//                )
//            call.respond(product)
//        }
//        post {
//            val product = call.receive<Product>()
//            productStorage.add(product)
//            call.respondText("Product stored correctly", status = HttpStatusCode.Created)
//        }
//        delete("{id?}") {
//            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
//            if (productStorage.removeIf { it.id == id }) {
//                call.respondText("Product removed correctly", status = HttpStatusCode.Accepted)
//            } else {
//                call.respondText("Not Found", status = HttpStatusCode.NotFound)
//            }
//        }
    }
}