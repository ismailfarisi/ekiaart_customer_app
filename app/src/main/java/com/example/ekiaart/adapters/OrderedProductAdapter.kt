package com.example.ekiaart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ekiaart.databinding.OrderedProductRecyclerviewItemBinding
import com.example.ekiaart.domain.Product

class OrderedProductAdapter :
    ListAdapter<Product, OrderedProductAdapter.OrderedProductViewHolder>(OrderedProductDiffcallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderedProductViewHolder {
        return OrderedProductViewHolder(
            OrderedProductRecyclerviewItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderedProductViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item)
    }

    class OrderedProductViewHolder(private val binding: OrderedProductRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.orderedProduct = product
            binding.executePendingBindings()
        }
    }
}

class OrderedProductDiffcallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        TODO("Not yet implemented")
    }

}
