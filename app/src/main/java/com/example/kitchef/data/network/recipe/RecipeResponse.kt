package com.example.kitchef.data.network.recipe


import com.example.kitchef.data.db.entity.recipeModel.Hit

data class RecipeResponse(
//    val count: Int,
//    val from: Int,
    val hits: List<Hit>,
//    @SerializedName("_links")
//    val links: LinksX,
//    val to: Int
)