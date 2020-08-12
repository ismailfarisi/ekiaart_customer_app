package com.example.ekiaart.domain

import com.google.firebase.firestore.GeoPoint

data class User(
    val userName: String? = null,
    val userPhone: Int? = null,
    val loaction: GeoPoint? = null
)