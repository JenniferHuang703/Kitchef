package com.app.kitchef.data.network.recipe

import com.app.kitchef.data.db.entity.recipeModel.Hit

data class RecipeResponse(
//    val count: Int,
//    val from: Int,
    val hits: List<Hit>,
//    @SerializedName("_links")
//    val links: LinksX,
//    val to: Int
)