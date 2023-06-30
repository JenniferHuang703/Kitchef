package com.app.kitchef.data.db.entity.recipeModel

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_recipe")
@Parcelize
data class FavoriteRecipeEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "recipeId")
    val recipeId: Int,

    @ColumnInfo(name = "recipeName")
    val recipeName: String,

    @ColumnInfo(name = "authorCredit")
    val authorCredit: String,

    @ColumnInfo(name = "recipeImageUrl")
    val recipeImageUrl: String
): Parcelable