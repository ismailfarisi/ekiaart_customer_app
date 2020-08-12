package com.example.ekiaart.domain


sealed class Result<out V> {
    data class Success<out V>(val data: V) : Result<V>()
    data class Error(val e: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}