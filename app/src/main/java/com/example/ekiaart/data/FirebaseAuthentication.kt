package com.example.ekiaart.data


import com.example.ekiaart.domain.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseAuthentication {


    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()


    @ExperimentalCoroutinesApi
    suspend fun firebaseAuthWithGoogle(idToken: String): Flow<Result<Boolean>> = callbackFlow {
        offer(Result.Loading)
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnSuccessListener {
            firestore.collection("users").document(it.user!!.uid).get().addOnCompleteListener {
                if (it.result != null) {
                    offer(Result.Success(true))
                    return@addOnCompleteListener
                } else {
                    offer(Result.Success(false))
                }
            }

        }.addOnFailureListener { ex ->
            offer(Result.Error(ex))
        }
        awaitClose()
    }
}
