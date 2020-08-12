package com.example.ekiaart.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ekiaart.ui.Auth.AuthActivity
import com.example.ekiaart.ui.home.MainActivity
import com.google.firebase.auth.FirebaseAuth


private const val TAG = "SplashScreenActivity"

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()


        val currentUser = auth.currentUser
        val uid = currentUser?.uid
        Log.d(TAG, "onCreate: $currentUser")
        if (currentUser == null) {
            val intent = Intent(this@SplashScreenActivity, AuthActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            intent.putExtra("uid", uid)
            startActivity(intent)
            finish()
        }

    }
}