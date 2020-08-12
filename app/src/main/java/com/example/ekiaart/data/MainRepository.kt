package com.example.ekiaart.data

import com.example.ekiaart.domain.NewOrderToShopDocument
import com.example.ekiaart.domain.ProductDetails
import com.example.ekiaart.domain.Result
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getProducts(shopId: String): Flow<Result<List<ProductDetails>>>
    suspend fun uploadNewOrder(
        newOrderToShopDocument: NewOrderToShopDocument,
        shopId: String
    ): Flow<Result<Unit>>
}