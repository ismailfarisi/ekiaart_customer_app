package com.example.ekiaart.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ekiaart.data.FirestoreData

@Suppress("UNCHECKED_CAST")
class ShopListViewModelFactory(private val repository: FirestoreData) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShopListViewModel(repository) as T
    }

}
