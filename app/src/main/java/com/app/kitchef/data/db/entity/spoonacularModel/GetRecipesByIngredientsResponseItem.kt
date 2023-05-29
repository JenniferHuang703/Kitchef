package com.app.kitchef.data.db.entity.spoonacularModel

import com.google.gson.annotations.SerializedName

data class GetRecipesByIngredientsResponseItem(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val imageUrl: String?,
    @SerializedName("likes")
    val likes: Int?,
    @SerializedName("title")
    val title: String?
)
