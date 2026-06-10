package com.example.coffeehouse.domain

data class CoffeeItem(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val price: Double = 0.0
)
