package com.example.ekiaart.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class ProductDetails(
    val productId: String? = null,
    val productName: String? = null,
    val price: Double? = null,
    val avgRating: Float? = null,
    val productDescription: String? = null
)

@Parcelize
data class ProductParcelable(
    val productId: String,
    val productName: String,
    val price: Double
) : Parcelable {
    val priceString = price.toString()
}