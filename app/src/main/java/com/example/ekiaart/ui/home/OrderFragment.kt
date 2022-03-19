package com.example.ekiaart.ui.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ekiaart.adapters.OrderedProductAdapter
import com.example.ekiaart.databinding.OrderFragmentBinding
import com.example.ekiaart.domain.NewOrderToShopDocument
import com.example.ekiaart.domain.Product
import com.example.ekiaart.domain.Result
import com.example.ekiaart.util.TAG
import kotlinx.coroutines.flow.collect

class OrderFragment : Fragment() {

    private lateinit var binding: OrderFragmentBinding
    private lateinit var viewModel: OrderViewModel
    private val args: OrderFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OrderFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId: String?
        val productList = args.productList.products
        val shopId = args.productList.shopId
        val shopName = args.productList.shopName
        binding.shopNameTxtview.text = shopName

        userId = "sdscsducbn"
        binding.placeOrderBtn.setOnClickListener {
            Log.d(TAG, "onViewCreated: button pressed")
            placeOrder(userId, shopId, productList)
        }


        val adapter = OrderedProductAdapter()
        binding.orderedProductRecyclerview.also {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = adapter
        }

        adapter.submitList(productList)
        showTotalPrice(productList)

    }

    private fun showTotalPrice(productList: List<Product>) {
        var total = 0.0
        for (product in productList) {
            total += product.product.price
        }
        binding.totalPriceTxtview.text = total.toString()
    }

    private fun placeOrder(userID: String, shopId: String, productList: List<Product>) {
        val newOrder = NewOrderToShopDocument(products = productList)
        lifecycleScope.launchWhenCreated {
            viewModel.uploadNewOrder(newOrder, shopId).collect { result ->
                when (result) {
                    is Result.Success -> Log.d(TAG, "placeOrder: success")
                }
            }
        }

    }


}