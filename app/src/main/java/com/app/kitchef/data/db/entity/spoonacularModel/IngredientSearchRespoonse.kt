package com.app.kitchef.data.db.entity.spoonacularModel

import com.google.gson.annotations.SerializedName

data class IngredientSearchResponse (
    @SerializedName("results")
    val results: List<IngredientSearchInformationResponse>
)