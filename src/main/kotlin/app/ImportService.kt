package org.example.app

import app.Product

object ImportService {
    private val productStock = mutableListOf<Product>()
    private var accountBalance = 100000.0

    fun importProducts(
        supplier: String,
        date: String,
        products: List<Product>
    ): Result<String> {
        val totalCost = products.sumOf { it.price * it.quantity }

        return when {
            totalCost > accountBalance -> Result.failure(Exception("Insufficient balance!"))
            products.isEmpty() -> Result.failure(Exception("No products to import!"))
            else -> {
                products.forEach { addOrUpdateStock(it) }
                accountBalance -= totalCost
                generateReport(supplier, date, products, totalCost)
                Result.success("Import successful! Remaining balance: $$accountBalance")
            }
        }
    }

    private fun addOrUpdateStock(newProduct: Product) {
        val existingProduct = productStock.find { it.title == newProduct.title }
        if (existingProduct != null) {
            existingProduct.quantity += newProduct.quantity
        } else {
            productStock.add(newProduct)
        }
    }

    private fun generateReport(
        supplier: String,
        date: String,
        products: List<Product>,
        totalSum: Double
    ) {
        val reportContent = buildString {
            appendLine("IMPORT REPORT")
            appendLine("Supplier: $supplier")
            appendLine("Date: $date")
            appendLine("Products:")
            products.forEach { appendLine("${it.title} - ${it.quantity} x ${it.price}") }
            appendLine("Total Sum: $totalSum")
        }
        println(reportContent)
    }
}