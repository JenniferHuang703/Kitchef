package com.app.kitchef.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

const val CURRENT_RECIPE_ID = 0

@Entity(tableName = "current_recipe")
@Parcelize
data class Recipe(
    @ColumnInfo(name = "recipeId")
    val recipeId: Int,
    @ColumnInfo(name = "label")
    val label: String,
    @ColumnInfo(name = "image")
    val image: String,
//   val cuisineType: List<String>
) : Parcelable {
    @PrimaryKey(autoGenerate = false)
    var id = CURRENT_RECIPE_ID
}
