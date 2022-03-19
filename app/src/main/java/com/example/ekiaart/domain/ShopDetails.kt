package com.example.ekiaart.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class ShopDetails(
    val shopId: String = "",
    val shopName: String = "",
    val location: String = "",
    val postCode: String = "",
    val category: String = "",
    val avgRating: Float = 3.0f,
    val caption: String = "",
    val imageURL: String = ""
)

@Parcelize
data class ShopParcelable(
    val shopId: String,
    val shopName: String
) : Parcelable
