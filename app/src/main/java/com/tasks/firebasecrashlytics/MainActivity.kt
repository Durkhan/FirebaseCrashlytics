package com.tasks.firebasecrashlytics

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.tasks.firebasecrashlytics.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding



    private val logger = FirebaseLogger(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val eventParams = mapOf(
            "change" to "category",
            "button_text" to "Next"
        )
        logger.logEvent("button_clicked", eventParams)

        // Log an exception with some custom keys
        val exception = RuntimeException("Something went wrong")
        val exceptionParams = mapOf(
            "screen" to "MainActivity",
            "user_id" to 1234
        )
        logger.logException(exception, exceptionParams)


         val signInLauncher = registerForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { res ->
            this.onSignInResult(res)
        }


        binding.sdk.setOnClickListener {
            startActivity(Intent(this,Signup::class.java))
        }


        binding.firebaseUi.setOnClickListener {

            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.PhoneBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())

            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            signInLauncher.launch(signInIntent)

        }
    }
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser
            val eventParams = mapOf(
                "user_email" to user?.email!!,
                "user_id" to user.uid
            )
            logger.logEvent("signed in", eventParams)

        } else {
            val exception = RuntimeException("User not signed")
            val exceptionParams = mapOf(
                "sign in" to "Application"
            )
            logger.logException(exception, exceptionParams)
        }
    }
}






