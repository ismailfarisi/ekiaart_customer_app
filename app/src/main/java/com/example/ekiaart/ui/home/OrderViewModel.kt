package com.example.ekiaart.ui.home

import androidx.lifecycle.ViewModel
import com.example.ekiaart.data.FirestoreData
import com.example.ekiaart.data.MainRepository
import com.example.ekiaart.domain.NewOrderToShopDocument
import com.example.ekiaart.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class OrderViewModel : ViewModel() {

    private val mainRepository: MainRepository

    init {
        mainRepository = FirestoreData()
    }

    fun uploadNewOrder(
        newOrderToShopDocument: NewOrderToShopDocument,
        shopId: String
    ): Flow<Result<Unit>> = flow {
        try {
            emitAll(mainRepository.uploadNewOrder(newOrderToShopDocument, shopId))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

}