package com.example.testing.data.db.entity.recipeModel


import com.google.gson.annotations.SerializedName

data class Hit(
    @SerializedName("_links")
    val links: Links,
    val recipe: Recipe
)