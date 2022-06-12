package com.example.testing.data.db.entity.ingredientModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_INGREDIENT_ID = 0

@Entity(tableName = "current_ingredient")
data class FoodX(
//    val category: String,
//    val categoryLabel: String,
//    val foodId: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "label")
    val label: String,
//    val nutrients: NutrientsX
){
    @PrimaryKey(autoGenerate = false)
    var id = CURRENT_INGREDIENT_ID
}