package com.example.ekiaart.domain

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.parcel.Parcelize

data class NewOrder(
    val timestamp: Timestamp,
    val orderId: String
)

@Parcelize
data class Product(
    val product: ProductParcelable,
    val quantity: Int
) : Parcelable


data class NewOrderToShopDocument(
    val products: List<Product>,
    var timestamp: Timestamp? = null,
    var orderId: String? = null,
    var userId: String? = null,
    var userName: String? = null,
    val location: GeoPoint? = null,
    val orderStatus: Int = 0
)

@Parcelize
data class ProductList(
    val products: List<Product>,
    val shopId: String,
    val shopName: String
) : Parcelable