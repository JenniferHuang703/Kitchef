package com.app.kitchef.data.db.entity.spoonacularModel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetRandomRecipesResponse (
    @Json(name = "recipes")
    val recipes: List<GetRandomRecipesInformationResponse>
)

