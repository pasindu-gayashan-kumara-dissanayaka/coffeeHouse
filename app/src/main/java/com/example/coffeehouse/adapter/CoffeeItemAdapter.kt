package com.example.coffeehouse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeehouse.R
import com.example.coffeehouse.databinding.ItemCoffeeBinding
import com.example.coffeehouse.domain.CoffeeItem

class CoffeeItemAdapter : ListAdapter<CoffeeItem, CoffeeItemAdapter.CoffeeViewHolder>(CoffeeDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        val binding = ItemCoffeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoffeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CoffeeViewHolder(
        private val binding: ItemCoffeeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CoffeeItem) {
            binding.tvCoffeeName.text = item.name
            binding.tvCoffeeDescription.text = item.description
            binding.tvCoffeePrice.text = binding.root.context.getString(R.string.price_format, item.price)

            Glide.with(binding.ivCoffeeImage)
                .load(item.imageUrl)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(binding.ivCoffeeImage)
        }
    }

    private object CoffeeDiffUtil : DiffUtil.ItemCallback<CoffeeItem>() {
        override fun areItemsTheSame(oldItem: CoffeeItem, newItem: CoffeeItem): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CoffeeItem, newItem: CoffeeItem): Boolean = oldItem == newItem
    }
}
