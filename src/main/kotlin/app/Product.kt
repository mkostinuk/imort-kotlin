package app

data class Product(
    val title: String,
    val category: String,
    val price: Double,
    var quantity: Int
)