package com.example.ekiaart.ui.Auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ekiaart.data.FirebaseAuthentication

@Suppress("UNCHECKED_CAST")
class AuthActivityViewModelFactory(private val repository: FirebaseAuthentication) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthActivityViewModel(repository) as T
    }

}
