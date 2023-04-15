package com.app.kitchef.data.db.entity.recipeModel

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

const val CURRENT_RECIPE_ID = 0

@Entity(tableName = "current_recipe")
@Parcelize
data class Recipe(
//    val calories: Double,
//    val cautions: List<String>,
    @ColumnInfo(name = "cuisineType")
    val cuisineType: List<String>,
//    val dietLabels: List<String>,
//    val digest: List<Digest>,
//    val dishType: List<String>,
//    val healthLabels: List<String>,
    @ColumnInfo(name = "image")
    val image: String,
//    val images: Images,
    @ColumnInfo(name = "ingredientLines")
    val ingredientLines: List<String>,
//    val ingredients: List<Ingredient>,
    @ColumnInfo(name = "label")
    val label: String,
//    val mealType: List<String>,
//    val shareAs: String,
//    val source: String,
//    val totalDaily: TotalDaily,
//    val totalNutrients: TotalNutrients,
//    val totalTime: Int,
//    val totalWeight: Double,
//    val uri: String,
//    val url: String,
//    val yield: Int
): Parcelable {
    @PrimaryKey(autoGenerate = false)
    var id = CURRENT_RECIPE_ID
}
