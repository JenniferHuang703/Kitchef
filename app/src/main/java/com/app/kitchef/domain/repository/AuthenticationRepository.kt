package com.app.kitchef.domain.repository

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticationRepository(private val context: Context) {
    private var auth: FirebaseAuth = Firebase.auth

    companion object {
        private const val TAG = "AuthRepository"
    }

    fun loginWithEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                } else {
                    Log.d(TAG, "signInWithEmail:failure", task.exception)
                }
            }
    }

    fun signUpWithEmail(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signUpWithEmail:success")
                    val user = auth.currentUser
                } else {
                    Log.d(TAG, "signUpWithEmail:failure", task.exception)
                }
            }
    }

    fun signOut() {
        auth.signOut()
    }
}