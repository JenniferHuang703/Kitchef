package com.example.testing.data.network.recipe


import com.example.testing.data.db.entity.recipeModel.Hit
import com.example.testing.data.db.entity.recipeModel.LinksX
import com.google.gson.annotations.SerializedName

data class RecipeResponse(
//    val count: Int,
//    val from: Int,
    val hits: List<Hit>,
//    @SerializedName("_links")
//    val links: LinksX,
//    val to: Int
)