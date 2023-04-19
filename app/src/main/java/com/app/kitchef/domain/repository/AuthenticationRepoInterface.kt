package com.app.kitchef.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo

interface AuthenticationRepoInterface {
    suspend fun signUp(userInfo: UserInfo)
    fun login(userInfo: UserInfo)
    suspend fun signOut()
    suspend fun removeFavoriteItem(recipeId: String, userId: String): Result<Boolean>
    suspend fun getUserData(userId: String): Result<UserInfo?>
    fun getFavorites(userId: String): Result<List<String>?>
    fun getFirebaseAuth(): FirebaseAuth
}