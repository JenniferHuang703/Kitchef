package com.app.kitchef.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

const val CURRENT_RECIPE_ID = 0

@Entity(tableName = "current_recipe")
@Parcelize
data class Recipe(
   val label: String?,
    val image: String?,
//   val cuisineType: List<String>
): Parcelable {
    @PrimaryKey(autoGenerate = false)
    var id = com.app.kitchef.data.db.entity.recipeModel.CURRENT_RECIPE_ID
}
