package com.app.kitchef.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticationRepository {
    private var firebaseAuth:FirebaseAuth = Firebase.auth

}