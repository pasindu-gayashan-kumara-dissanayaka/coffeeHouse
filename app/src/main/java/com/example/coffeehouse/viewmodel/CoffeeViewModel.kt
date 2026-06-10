package com.example.coffeehouse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.coffeehouse.domain.Category
import com.example.coffeehouse.domain.CoffeeItem
import com.example.coffeehouse.repository.CoffeeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class CoffeeUiState(
    val categories: List<Category> = emptyList(),
    val popularItems: List<CoffeeItem> = emptyList(),
    val specialOffers: List<CoffeeItem> = emptyList()
)

class CoffeeViewModel(
    repository: CoffeeRepository
) : ViewModel() {

    val uiState: StateFlow<CoffeeUiState> = combine(
        repository.observeCategories(),
        repository.observePopularItems(),
        repository.observeSpecialOffers()
    ) { categories, popularItems, specialOffers ->
        CoffeeUiState(categories, popularItems, specialOffers)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CoffeeUiState()
    )
}

class CoffeeViewModelFactory(
    private val repository: CoffeeRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoffeeViewModel::class.java)) {
            return CoffeeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
