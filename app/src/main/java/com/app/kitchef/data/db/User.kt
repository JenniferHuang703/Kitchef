package com.app.kitchef.data.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName="users")
data class User(
    @PrimaryKey
    var userId: String = "",
    var userName: String = "",
    var email: String = "",
    var password: String = "",
    var favoriteList: List<String> = ArrayList(),
) : Parcelable {
    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "userId" to userId,
            "userName" to userName,
            "email" to email,
            "password" to password,
            "favoriteList" to favoriteList
        )
    }
}