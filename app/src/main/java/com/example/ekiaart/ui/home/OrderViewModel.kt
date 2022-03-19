package com.example.ekiaart.ui.home

import androidx.lifecycle.ViewModel
import com.example.ekiaart.data.FirestoreData
import com.example.ekiaart.data.MainRepository
import com.example.ekiaart.domain.NewOrderToShopDocument

class OrderViewModel : ViewModel() {

    private val mainRepository: MainRepository

    init {
        mainRepository = FirestoreData()
    }

    suspend fun uploadNewOrder(
        newOrderToShopDocument: NewOrderToShopDocument,
        shopId: String
    ) = mainRepository.uploadNewOrder(newOrderToShopDocument, shopId)


}