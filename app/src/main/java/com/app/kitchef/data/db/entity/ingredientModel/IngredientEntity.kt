package com.app.kitchef.data.db.entity.ingredientModel

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "current_ingredient")
@Parcelize
data class IngredientEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ingredientId")
    val ingredientId: Int,

    @ColumnInfo(name ="name")
    val name: String?,

    @ColumnInfo(name ="image")
    val image: String?,
): Parcelable