package com.example.ekiaart.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.ekiaart.data.FirestoreData
import com.example.ekiaart.domain.ShopDetails

class ShopListViewModel(
    private val repository: FirestoreData
) : ViewModel() {

    val shopsList: LiveData<List<ShopDetails>> = repository.getShops()
}