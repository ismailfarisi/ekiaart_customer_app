package com.example.ekiaart.ui.home

import androidx.lifecycle.ViewModel
import com.example.ekiaart.data.FirestoreData
import com.example.ekiaart.data.MainRepository
import com.example.ekiaart.domain.Product


class ProductListViewModel : ViewModel() {
    private val repository: MainRepository

    init {
        repository = FirestoreData()
    }

    val products: List<Product> get() = _products
    val _products = mutableListOf<Product>()

    fun addProductToProductList(product: Product) {
        _products.add(product)
    }

    suspend fun getProducts(shopId: String) = repository.getProducts(shopId)


}