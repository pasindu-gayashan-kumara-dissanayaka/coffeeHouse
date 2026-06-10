package com.example.coffeehouse.repository

import com.example.coffeehouse.domain.Category
import com.example.coffeehouse.domain.CoffeeItem
import kotlinx.coroutines.flow.Flow

interface CoffeeRepository {
    fun observeCategories(): Flow<List<Category>>
    fun observePopularItems(): Flow<List<CoffeeItem>>
    fun observeSpecialOffers(): Flow<List<CoffeeItem>>
}
