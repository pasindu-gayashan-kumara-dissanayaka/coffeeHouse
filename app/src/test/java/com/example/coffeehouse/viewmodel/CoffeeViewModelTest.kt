package com.example.coffeehouse.viewmodel

import com.example.coffeehouse.domain.Category
import com.example.coffeehouse.domain.CoffeeItem
import com.example.coffeehouse.repository.CoffeeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CoffeeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `uiState reflects repository updates`() = runTest {
        val fakeRepository = FakeCoffeeRepository()
        val viewModel = CoffeeViewModel(fakeRepository)

        fakeRepository.categories.value = listOf(Category(id = "1", name = "Espresso"))
        fakeRepository.popularItems.value = listOf(CoffeeItem(id = "2", name = "Latte", price = 4.5))
        fakeRepository.specialOffers.value = listOf(CoffeeItem(id = "3", name = "Mocha", price = 3.5))

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(1, state.categories.size)
        assertEquals("Latte", state.popularItems.first().name)
        assertEquals("Mocha", state.specialOffers.first().name)
    }
}

private class FakeCoffeeRepository : CoffeeRepository {
    val categories = MutableStateFlow<List<Category>>(emptyList())
    val popularItems = MutableStateFlow<List<CoffeeItem>>(emptyList())
    val specialOffers = MutableStateFlow<List<CoffeeItem>>(emptyList())

    override fun observeCategories(): Flow<List<Category>> = categories

    override fun observePopularItems(): Flow<List<CoffeeItem>> = popularItems

    override fun observeSpecialOffers(): Flow<List<CoffeeItem>> = specialOffers
}
