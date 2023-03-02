package com.tasks.firebasecrashlytics

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tasks.firebasecrashlytics.databinding.ActivitySigninBinding

class Signin : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivitySigninBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.signIn.setOnClickListener {
            signin()
        }
    }

    private fun signin() {
        val email=binding.email.text.toString()
        val password=binding.password.text.toString()
     auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Successfully sign in",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("signInWithEmail:failure", ""+task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }
}