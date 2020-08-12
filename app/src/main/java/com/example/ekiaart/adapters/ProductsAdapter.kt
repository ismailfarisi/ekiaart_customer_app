package com.example.ekiaart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ekiaart.databinding.ProductListItemBinding
import com.example.ekiaart.domain.ProductDetails
import com.example.ekiaart.domain.ProductParcelable
import com.example.ekiaart.ui.home.ProductListFragmentDirections


class ProductsAdapter :
    ListAdapter<ProductDetails, ProductsAdapter.ProductListViewHolder>(ProductsDiffCallbacks()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        return ProductListViewHolder(
            ProductListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ProductListViewHolder(private val binding: ProductListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { view ->
                binding.productDetails?.let {
                    val productParcelable =
                        ProductParcelable(it.productId!!, it.productName!!, it.price!!)
                    val action =
                        ProductListFragmentDirections.actionProductListFragmentToOrderQuantityFragment(
                            productParcelable
                        )
                    view.findNavController().navigate(action)
                }
            }
        }

        fun bind(productDetails: ProductDetails) {
            binding.productDetails = productDetails
            binding.executePendingBindings()
        }
    }
}

class ProductsDiffCallbacks : DiffUtil.ItemCallback<ProductDetails>() {
    override fun areItemsTheSame(oldItem: ProductDetails, newItem: ProductDetails): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ProductDetails, newItem: ProductDetails): Boolean {
        return oldItem.productId == newItem.productId
    }

}