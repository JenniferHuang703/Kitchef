package com.app.kitchef.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

const val CURRENT_RECIPE_ID = 0

@Entity(tableName = "current_recipe_detail")
@Parcelize
data class RecipeDetail(
    @ColumnInfo(name = "likesCount")
    val likesCount: Int,
    @ColumnInfo(name = "dishImageUrl")
    val dishImageUrl: String,
    @ColumnInfo(name = "cookingTime")
    val cookingTime: Int,
    @ColumnInfo(name = "dishName")
    val dishName: String,
    @ColumnInfo(name = "dishId")
    val dishId: Int,
    @ColumnInfo(name = "creditText")
    val creditText: String,
    @ColumnInfo(name = "shortInstruction")
    val shortInstruction: String,
//    @ColumnInfo(name = "steps")
//    val steps: List<Step> = listOf(),
//    @ColumnInfo(name = "nutrients")
//    val nutrients: List<Nutrient> = listOf(),
//    @ColumnInfo(name = "ingredients")
//    val ingredients: List<IngredientFull> = listOf(),
    @ColumnInfo(name = "isGlutenFree")
    val isGlutenFree: Boolean,
    @ColumnInfo(name = "isDairyFree")
    val isDairyFree: Boolean,
    @ColumnInfo(name = "isVegetarian")
    val isVegetarian: Boolean
): Parcelable {
    @PrimaryKey(autoGenerate = false)
    var id = CURRENT_RECIPE_ID
}
@Parcelize
data class Step(
    val number: Int,
    val stepInstruction: String
): Parcelable

@Parcelize
data class Nutrient(
    val title: String,
    val amount: Double,
    val unit: String,
    val percentOfDailyNeeds: Double
): Parcelable

@Parcelize
data class IngredientFull(
    val id: Int,
    val imageHalfPath: String,
    val name: String,
    val desc: String
): Parcelable