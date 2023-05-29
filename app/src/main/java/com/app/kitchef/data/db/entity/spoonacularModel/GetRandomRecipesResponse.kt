package com.app.kitchef.data.db.entity.spoonacularModel

import com.google.gson.annotations.SerializedName

data class GetRandomRecipesResponse (
    @SerializedName("recipes")
    val recipes: List<GetRandomRecipesInformationResponse>
)

