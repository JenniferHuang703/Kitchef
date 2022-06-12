package com.example.kitchef.data.network.ingredient


import com.example.kitchef.data.db.entity.ingredientModel.Parsed

data class IngredientResponse(
//    val hints: List<Hint>,
//    @SerializedName("_links")
//    val links: Links,
//    @Embedded(prefix = "parsed_")
    val parsed: List<Parsed>
//    val text: String
)