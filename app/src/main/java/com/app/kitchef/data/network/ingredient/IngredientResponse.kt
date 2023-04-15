package com.app.kitchef.data.network.ingredient


import com.app.kitchef.data.db.entity.ingredientModel.Parsed

data class IngredientResponse(
//    val hints: List<Hint>,
//    @SerializedName("_links")
//    val links: Links,
//    @Embedded(prefix = "parsed_")
    val parsed: List<Parsed>
//    val text: String
)