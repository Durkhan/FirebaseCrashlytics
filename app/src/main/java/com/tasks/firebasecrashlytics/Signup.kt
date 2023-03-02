package com.tasks.firebasecrashlytics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tasks.firebasecrashlytics.databinding.ActivitySignupBinding

class Signup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignupBinding
    private val logger = FirebaseLogger(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        binding.signUp.setOnClickListener {
            signup()
        }
    }

    private fun signup() {
        val email=binding.email.text.toString()
        val password=binding.password.text.toString()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val eventParams= mapOf(
                        user.toString() to "signup"
                    )
                    logger.logEvent("createUserWithEmailAndPassword",eventParams)

                    Toast.makeText(baseContext, "Successfully sign up",
                        Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this,Signin::class.java))
                } else {
                    val exception = Exception("createUserWithEmailAndPassword")
                    val eventParams= mapOf(
                        "user" to "signup failed"
                    )
                    logger.logException(exception,eventParams)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }
}