package com.app.kitchef.data.db.entity.spoonacularModel

import com.google.gson.annotations.SerializedName

data class GetIngredientResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("image")
    val image: String?,
)