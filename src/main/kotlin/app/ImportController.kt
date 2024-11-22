package app

import org.example.app.ImportService

class ImportController {

    fun handleImport() {
        println("Enter supplier name:")
        val supplier = readln()
        println("Enter date (yyyy-mm-dd):")
        val date = readln()

        val products = mutableListOf<Product>()
        while (true) {
            println("Enter product details (title, category, price, quantity) or 'done' to finish:")
            val input = readln()
            if (input == "done") break
            val (title, category, price, quantity) = input.split(", ")
            products.add(Product(title, category, price.toDouble(), quantity.toInt()))
        }

        val result = ImportService.importProducts(supplier, date, products)
        result.fold(
            onSuccess = { println(it) },
            onFailure = { println("Error: ${it.message}") }
        )
    }
}