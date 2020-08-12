package com.example.ekiaart.data


import com.example.ekiaart.domain.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseAuthentication {


    private val auth = FirebaseAuth.getInstance()


    @ExperimentalCoroutinesApi
    suspend fun firebaseAuthWithGoogle(idToken: String): Flow<Result<Unit>> = callbackFlow {
        offer(Result.Loading)
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnSuccessListener {
            offer(Result.Success(Unit))
        }.addOnFailureListener { ex ->
            offer(Result.Error(ex))
        }
        awaitClose()
    }
}
