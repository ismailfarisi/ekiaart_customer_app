package com.example.ekiaart.ui.Auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.ekiaart.R
import com.example.ekiaart.data.FirebaseAuthentication
import com.example.ekiaart.databinding.ActivityAuthBinding
import com.example.ekiaart.domain.Result
import com.example.ekiaart.ui.home.MainActivity
import com.example.ekiaart.util.RC_SIGN_IN
import com.example.ekiaart.util.TAG
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthActivityViewModel
    private lateinit var factory: AuthActivityViewModelFactory
    private lateinit var binding: ActivityAuthBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        factory = AuthActivityViewModelFactory(FirebaseAuthentication())

        viewModel = ViewModelProvider(this, factory).get(AuthActivityViewModel::class.java)

        requestGoogleSignIn()



        binding.googleSignInBtn.setOnClickListener {
            signIn()
        }
    }

    private fun requestGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }


    private fun signIn() {
        Log.d(TAG, "signIn: button pressed")
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(
            signInIntent,
            RC_SIGN_IN
        )
        binding.progressBar.visibility = View.VISIBLE
        binding.googleSignInBtn.visibility = View.GONE
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                receiveLoginStatus(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }

    }


    private fun receiveLoginStatus(idToken: String) {
        lifecycleScope.launch {
            viewModel.firebaseAuth(idToken).collect { result ->
                when (result) {
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        if (result.data) {
                            Intent(this@AuthActivity, MainActivity::class.java).apply {
                                startActivity(this)
                                finish()
                            }
                        } else {

                        }
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.googleSignInBtn.visibility = View.VISIBLE
                        Toast.makeText(this@AuthActivity, "Sign In Failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                    is Result.Loading -> {

                    }
                }
            }
        }
    }


}