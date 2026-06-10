package com.example.coffeehouse.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeehouse.adapter.CategoryAdapter
import com.example.coffeehouse.adapter.CoffeeItemAdapter
import com.example.coffeehouse.databinding.ActivityMainBinding
import com.example.coffeehouse.repository.FirebaseCoffeeRepository
import com.example.coffeehouse.viewmodel.CoffeeViewModel
import com.example.coffeehouse.viewmodel.CoffeeViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val categoryAdapter = CategoryAdapter()
    private val popularAdapter = CoffeeItemAdapter()
    private val offerAdapter = CoffeeItemAdapter()

    private val viewModel: CoffeeViewModel by viewModels {
        CoffeeViewModelFactory(FirebaseCoffeeRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setupLists()
        observeUi()
    }

    private fun setupLists() {
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        binding.rvPopularItems.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }

        binding.rvSpecialOffers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = offerAdapter
        }
    }

    private fun observeUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    categoryAdapter.submitList(state.categories)
                    popularAdapter.submitList(state.popularItems)
                    offerAdapter.submitList(state.specialOffers)
                }
            }
        }
    }
}
