package com.example.testing.data.db.entity.recipeModel


import com.google.gson.annotations.SerializedName

data class Ingredient(
    val food: String,
    val foodCategory: String,
    val foodId: String,
    val image: String,
    val measure: String,
    val quantity: Double,
    val text: String,
    val weight: Double
)