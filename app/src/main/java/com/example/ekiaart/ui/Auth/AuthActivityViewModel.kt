package com.example.ekiaart.ui.Auth

import androidx.lifecycle.ViewModel
import com.example.ekiaart.data.FirebaseAuthentication
import com.example.ekiaart.domain.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class AuthActivityViewModel(private val repository: FirebaseAuthentication) : ViewModel() {


    @ExperimentalCoroutinesApi
    suspend fun firebaseAuth(idToken: String): Flow<Result<Boolean>> =
        repository.firebaseAuthWithGoogle(idToken)


}