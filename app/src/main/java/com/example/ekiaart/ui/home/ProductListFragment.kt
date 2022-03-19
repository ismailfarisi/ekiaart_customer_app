package com.example.ekiaart.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ekiaart.adapters.ProductsAdapter
import com.example.ekiaart.databinding.ProductListFragmentBinding
import com.example.ekiaart.domain.ProductList
import com.example.ekiaart.domain.Result
import com.example.ekiaart.util.TAG
import kotlinx.coroutines.flow.collect

class ProductListFragment : Fragment() {


    private lateinit var viewModel: ProductListViewModel
    private lateinit var binding: ProductListFragmentBinding
    private val args: ProductListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductListFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(ProductListViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shopId = args.shopParcelable.shopId
        val shopName = args.shopParcelable.shopName
        val adapter = ProductsAdapter()
        binding.productRecyclerView.also {
            it.layoutManager = LinearLayoutManager(this.context)
            it.adapter = adapter
        }
        subscribeUI(adapter, shopId)
        binding.orderNowBtn.setOnClickListener { thisView ->
            ifProductsAddedGotoOrderFragment(thisView, shopId, shopName)
        }

    }

    private fun ifProductsAddedGotoOrderFragment(view: View, shopId: String, shopName: String) {
        val productList = viewModel.products
        if (productList.isNullOrEmpty()) {
            Toast.makeText(this.context, "You must add product", Toast.LENGTH_SHORT).show()
        } else {
            val action = ProductListFragmentDirections.actionProductListFragmentToOrderFragment(
                ProductList(
                    productList,
                    shopId,
                    shopName
                )
            )
            view.findNavController().navigate(action)
        }
    }


    private fun subscribeUI(adapter: ProductsAdapter, shopId: String) {
        lifecycleScope.launchWhenCreated {
            Log.d(TAG, "subscribeUI: productlist")
            viewModel.getProducts(shopId).collect { result ->
                when (result) {
                    is Result.Success -> {
                        binding.progressBar2.visibility = View.GONE
                        binding.productRecyclerView.visibility = View.VISIBLE
                        adapter.submitList(result.data)
                    }
                    is Result.Error -> {
                        binding.progressBar2.visibility = View.GONE
                        Toast.makeText(
                            this@ProductListFragment.context,
                            "Error Loading Products",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Result.Loading -> {
                    }
                }
            }
        }
    }
}