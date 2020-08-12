package com.example.ekiaart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ekiaart.databinding.ShopListItemBinding
import com.example.ekiaart.domain.ShopDetails
import com.example.ekiaart.domain.ShopParcelable
import com.example.ekiaart.ui.home.ShopListFragmentDirections

class ShopsAdapter : ListAdapter<ShopDetails, RecyclerView.ViewHolder>(ShopsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShopDetailsViewHolder(
            ShopListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val shop = getItem(position)
        (holder as ShopDetailsViewHolder).bind(shop)
    }

    private class ShopDetailsViewHolder(private val binding: ShopListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { view ->
                binding.shopDetails?.let { shopDetails ->
                    val shopParcelable = ShopParcelable(shopDetails.shopId, shopDetails.shopName)
                    val action =
                        ShopListFragmentDirections.actionShopListFragmentToProductListFragment(
                            shopParcelable
                        )
                    view.findNavController().navigate(action)
                }
            }
        }

        fun bind(shopsDetails: ShopDetails) {
            binding.shopDetails = shopsDetails
            binding.executePendingBindings()
        }
    }
}

class ShopsDiffCallback : DiffUtil.ItemCallback<ShopDetails>() {
    override fun areItemsTheSame(oldItem: ShopDetails, newItem: ShopDetails): Boolean {
        return oldItem.shopName == newItem.shopName
    }

    override fun areContentsTheSame(oldItem: ShopDetails, newItem: ShopDetails): Boolean {
        return oldItem == newItem
    }

}
