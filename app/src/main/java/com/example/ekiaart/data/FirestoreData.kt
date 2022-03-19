package com.example.ekiaart.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ekiaart.domain.NewOrderToShopDocument
import com.example.ekiaart.domain.ProductDetails
import com.example.ekiaart.domain.Result
import com.example.ekiaart.domain.ShopDetails
import com.example.ekiaart.util.ORDER_DETAILS
import com.example.ekiaart.util.PRODUCT_DETAILS
import com.example.ekiaart.util.SHOP_DETAILS
import com.example.ekiaart.util.TAG
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class FirestoreData : MainRepository {


    private val firestore = FirebaseFirestore.getInstance()


    fun getShops(): LiveData<List<ShopDetails>> {
        Log.d(TAG, "getShops: started")
        val liveData = MutableLiveData<List<ShopDetails>>()
        try {
            firestore.collection(SHOP_DETAILS).whereEqualTo("status", true).get()
                .addOnSuccessListener {
                    val list = mutableListOf<ShopDetails>()
                    for (document in it) {
                        list.add(document.toObject())
                    }
                    liveData.postValue(list)
                }
        } catch (e: Exception) {
        }
        return liveData
    }

    @ExperimentalCoroutinesApi
    override suspend fun getProducts(shopId: String): Flow<Result<List<ProductDetails>>> =
        callbackFlow {
            offer(Result.Loading)
            Log.d(TAG, "getProducts: started uid :$shopId")
            try {
                firestore.collection(SHOP_DETAILS).document(shopId).collection(PRODUCT_DETAILS)
                    .get()
                    .addOnSuccessListener { snapshot ->
                        try {
                            val mutableList = mutableListOf<ProductDetails>()
                            for (product in snapshot) {
                                val data = product.toObject<ProductDetails>()
                                mutableList.add(data)
                                Log.d(TAG, "getProducts: $data")
                            }
                            offer(Result.Success(mutableList))
                        } catch (e: Exception) {
                            offer(Result.Error(e))
                        }
                    }.addOnFailureListener { e ->
                        offer(Result.Error(e))
                        Log.e(TAG, "getProducts: failed", e)
                    }
            } catch (e: Exception) {
                offer(Result.Error(e))
                Log.e(TAG, "getProducts: failed", e)
            }
            awaitClose()
        }

    @ExperimentalCoroutinesApi
    override suspend fun uploadNewOrder(
        newOrderToShopDocument: NewOrderToShopDocument,
        shopId: String
    ): Flow<Result<Unit>> = callbackFlow {
        val orderToShopRef =
            firestore.collection(SHOP_DETAILS).document(shopId).collection(ORDER_DETAILS).document()
        val orderId = orderToShopRef.id
        val timestamp = Timestamp.now()
        newOrderToShopDocument.orderId = orderId
        newOrderToShopDocument.timestamp = timestamp
        orderToShopRef.set(newOrderToShopDocument).addOnSuccessListener {
            Log.d(TAG, "uploadNewOrder: success")
        }.addOnFailureListener {
            Log.e(TAG, "uploadNewOrder: failed", it)
        }

        awaitClose()

    }


}