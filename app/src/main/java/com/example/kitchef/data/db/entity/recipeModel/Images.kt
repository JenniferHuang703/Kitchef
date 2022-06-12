package com.example.kitchef.data.db.entity.recipeModel


import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("LARGE")
    val lARGE: LARGE,
    @SerializedName("REGULAR")
    val rEGULAR: REGULAR,
    @SerializedName("SMALL")
    val sMALL: SMALL,
    @SerializedName("THUMBNAIL")
    val tHUMBNAIL: THUMBNAIL
)