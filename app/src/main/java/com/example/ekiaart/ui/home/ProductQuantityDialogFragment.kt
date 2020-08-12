package com.example.ekiaart.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.ekiaart.databinding.OrderQuantityFragmentBinding
import com.example.ekiaart.domain.Product
import com.example.ekiaart.domain.ProductParcelable

class ProductQuantityDialogFragment : DialogFragment() {

    private lateinit var viewModel: ProductListViewModel
    private lateinit var binding: OrderQuantityFragmentBinding
    private val args: ProductQuantityDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OrderQuantityFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(ProductListViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var num = 1
        val product = args.productParcelable

        binding.addImageButton.setOnClickListener {
            num++
            binding.number.text = num.toString()
        }
        binding.minusImageButton.setOnClickListener {
            if (num > 1) {
                num--
                binding.number.text = num.toString()
            }
        }
        binding.addButton.setOnClickListener {
            addproductToList(num, product)
            dismiss()
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }


    }

    private fun addproductToList(num: Int, productParcelable: ProductParcelable) {
        val product = Product(productParcelable, num)
        viewModel.addProductToProductList(product)
    }
}